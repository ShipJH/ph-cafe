package ph.cafe.io.domain.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ph.cafe.io.PasswordCheckUtils.passwordValidCheck
import ph.cafe.io.domain.user.model.UserDto
import ph.cafe.io.domain.user.model.UserEntity
import java.sql.SQLException

@Service
class UserService(private val userRepository: UserRepository) {

    @Transactional
    fun signUp(request: UserDto.SignUpRequest) {
        passwordValidCheck(request.password, request.passwordCheck)
        userRepository.findByPhoneNumber(request.getRemoveDashPhoneNumber()).ifPresent {
            throw IllegalArgumentException("이미 가입된 번호입니다.")
        }

        try {
            userRepository.save(
                UserEntity(
                    request.getRemoveDashPhoneNumber(),
                    request.password
                )
            )
        } catch (e: SQLException) {
            throw IllegalArgumentException("회원가입에 실패했습니다.[DB]에러")
        } catch (e: Exception) {
            throw IllegalArgumentException("회원가입에 실패했습니다.")
        }
    }

}