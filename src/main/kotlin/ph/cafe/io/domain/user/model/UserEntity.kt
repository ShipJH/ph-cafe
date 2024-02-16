package ph.cafe.io.domain.user.model

import jakarta.persistence.*
import ph.cafe.io.common.BaseEntity

@Entity
@Table(name = "users")
class UserEntity(
    @Column(length = 13, nullable = false, unique = true)
    var phoneNumber: String,

    @Column(length = 100, nullable = false)
    var password: String,

    @Enumerated(value = EnumType.STRING)
    var role: UserEnum.Role = UserEnum.Role.OWNER,

    var isOnLine: Boolean = false

): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

}