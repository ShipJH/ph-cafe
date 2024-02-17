package ph.cafe.io.domain.user

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import ph.cafe.io.domain.user.model.UserEntity
import ph.cafe.io.exception.BaseException
import ph.cafe.io.exception.ExceptionCode

@Service
class UserEntityGetService {

    fun getUserEntity(): UserEntity {
        try {
            val userDetail = SecurityContextHolder.getContext().authentication.principal as CustomUserDetails
            return userDetail.getUser()
        } catch (e: Exception) {
            throw BaseException(ExceptionCode.USER_NOT_FOUND)
        }
    }

    fun loginUserCheck(requestUserId: Long) {
        val userEntity = getUserEntity()
        if(userEntity.id != requestUserId) {
            throw BaseException(ExceptionCode.USER_NOT_AUTHORIZED)
        }
    }

}