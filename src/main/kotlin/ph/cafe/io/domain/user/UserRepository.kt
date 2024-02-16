package ph.cafe.io.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import ph.cafe.io.domain.user.model.UserEntity
import java.util.Optional

interface UserRepository: JpaRepository<UserEntity, Long> {
    fun findByPhoneNumber(phoneNumber: String): Optional<UserEntity>
}