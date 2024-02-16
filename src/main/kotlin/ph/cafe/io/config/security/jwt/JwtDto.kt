package ph.cafe.io.config.security.jwt

import jakarta.validation.constraints.NotBlank

class JwtDto {

    data class Response(
        val grantType: String,
        val accessToken: String,
        val refreshToken: String,
        val accessTokenExpiresIn: Long
    )

    data class JwtReissueRequest(
        @field:NotBlank val refreshToken: String,
    )

}