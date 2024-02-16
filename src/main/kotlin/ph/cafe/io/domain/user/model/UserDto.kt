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

}