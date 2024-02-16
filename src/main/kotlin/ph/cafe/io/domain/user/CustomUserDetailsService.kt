package ph.cafe.io.domain.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import ph.cafe.io.domain.user.model.UserEntity
import ph.cafe.io.exception.BaseException
import ph.cafe.io.exception.ExceptionCode

@Service
class CustomUserDetailsService(private val userRepository: UserRepository): UserDetailsService {

    override fun loadUserByUsername(username: String): CustomUserDetails {
        userRepository.findByPhoneNumber(username).orElseThrow {
            throw BaseException(ExceptionCode.USER_NOT_FOUND)
        }.let {
            return CustomUserDetails(it)
        }
    }

    fun loadUserById(userId: Long): CustomUserDetails {
        userRepository.findById(userId).orElseThrow {
            throw BaseException(ExceptionCode.USER_NOT_FOUND)
        }.let {
            return CustomUserDetails(it)
        }
    }


}

class CustomUserDetails(private val user: UserEntity): UserDetails {

    fun getUser(): UserEntity {
        return user
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("ROLE_${user.role}"))
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return user.phoneNumber
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}