package ph.cafe.io.domain.user

import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ph.cafe.io.common.ResponseDto
import ph.cafe.io.config.PasswordEncodeConfig
import ph.cafe.io.config.security.jwt.JwtProvider
import ph.cafe.io.utils.ValidUtils.passwordValidCheck
import ph.cafe.io.domain.user.model.UserDto
import ph.cafe.io.domain.user.model.UserEntity
import ph.cafe.io.domain.user.model.UserEnum
import ph.cafe.io.exception.BaseException
import ph.cafe.io.exception.ExceptionCode
import ph.cafe.io.utils.ValidUtils.phoneNumberValidCheck
import java.sql.SQLException

@Service
class UserService(private val userRepository: UserRepository,
                  private val passwordEncode: PasswordEncodeConfig,
                  private val jwtProvider: JwtProvider,
                  private val userEntityGetService: UserEntityGetService
) {

    @Transactional
    fun signUp(request: UserDto.SignUpRequest): ResponseDto.Response {
        phoneNumberValidCheck(request.phoneNumber)
        passwordValidCheck(request.password, request.passwordCheck)
        userRepository.findByPhoneNumber(request.getRemoveDashPhoneNumber()).ifPresent {
            throw BaseException(ExceptionCode.ALREADY_REGISTERED)
        }
        try {

            val userEntity = UserEntity(
                phoneNumber = request.getRemoveDashPhoneNumber(),
                password = passwordEncode.passwordEncoder().encode(request.password),
                role = UserEnum.Role.OWNER
            )
            userRepository.save(userEntity)

            return ResponseDto.Response(
                meta = ResponseDto.Meta(
                    code = HttpStatus.OK.value(),
                ),
                data = UserDto.SignUpResponse(
                    id = userEntity.id,
                    phoneNumber = userEntity.phoneNumber,
                    role = userEntity.role
                )
            )

        } catch (e: SQLException) {
            throw BaseException(ExceptionCode.SQL_ERROR)
        } catch (e: Exception) {
            throw BaseException(ExceptionCode.SAVE_FAIL)
        }
    }

    @Transactional
    fun signIn(request: UserDto.LoginRequest): ResponseDto.Response {
        val userEntity = userRepository.findByPhoneNumber(request.getRemoveDashPhoneNumber()).orElseThrow {
            throw BaseException(ExceptionCode.USER_NOT_FOUND)
        }
        if (!passwordEncode.passwordEncoder().matches(request.password, userEntity.password)) {
            throw BaseException(ExceptionCode.NOT_MATCH_PASSWORD)
        }
        try {
            val authentication = UsernamePasswordAuthenticationToken(request.getRemoveDashPhoneNumber(), request.password)

            val jwtToken = jwtProvider.createJwtToken(userEntity, authentication)

            userEntity.isOnLine = true
            userEntity.refreshToken = jwtToken.refreshToken
            userRepository.save(userEntity)

            return ResponseDto.Response(
                meta = ResponseDto.Meta(
                    code = HttpStatus.OK.value(),
                ),
                data = jwtToken
            )
        } catch (e: Exception) {
            throw BaseException(ExceptionCode.INTERNAL_SERVER_ERROR)
        }
    }

    @Transactional
    fun signOut(): ResponseDto.Response {
        try {
            val userEntity = userEntityGetService.getUserEntity()
            userEntity.isOnLine = false
            userRepository.save(userEntity)
            return ResponseDto.Response(
                meta = ResponseDto.Meta(
                    code = HttpStatus.OK.value(),
                ),
                data = null
            )
        } catch (e: Exception) {
            throw BaseException(ExceptionCode.INTERNAL_SERVER_ERROR)
        }
    }

    @Transactional
    fun reissue(request: UserDto.ReissueRequest): ResponseDto.Response {
        try {
            val authentication = jwtProvider.getAuthentication(request.refreshToken)
            val userEntity = authentication.second
            if(request.refreshToken.replace("bearer ", "") != userEntity.refreshToken) {
                throw BaseException(ExceptionCode.NOT_MATCH_REFRESH_TOKEN)
            }
            val jwtToken = jwtProvider.createJwtToken(authentication.second, authentication.first)

            userEntity.refreshToken = jwtToken.refreshToken
            userRepository.save(userEntity)

            return ResponseDto.Response(
                meta = ResponseDto.Meta(
                    code = HttpStatus.OK.value(),
                ),
                data = jwtToken
            )
        } catch (e: Exception) {
            throw BaseException(ExceptionCode.INTERNAL_SERVER_ERROR)
        }
    }

}