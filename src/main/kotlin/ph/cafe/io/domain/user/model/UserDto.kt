package ph.cafe.io.domain.user.model

import jakarta.validation.constraints.NotBlank
import ph.cafe.io.common.Constants.DASH

class UserDto {

    data class SignUpRequest(
        @field:NotBlank val phoneNumber: String,
        @field:NotBlank val password: String,
        @field:NotBlank val passwordCheck: String
    ) {
        fun getRemoveDashPhoneNumber(): String {
            return this.phoneNumber.replace(DASH, "")
        }
    }

    data class SignUpResponse(
        val id: Long,
        val phoneNumber: String,
        val role: UserEnum.Role
    )

    data class LoginRequest(
        @field:NotBlank val phoneNumber: String,
        @field:NotBlank val password: String
    ) {
        fun getRemoveDashPhoneNumber(): String {
            return this.phoneNumber.replace(DASH, "")
        }
    }

}