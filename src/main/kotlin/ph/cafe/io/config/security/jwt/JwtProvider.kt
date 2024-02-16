package ph.cafe.io.config.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import ph.cafe.io.domain.user.CustomUserDetailsService
import ph.cafe.io.domain.user.model.UserEntity
import ph.cafe.io.exception.BaseException
import ph.cafe.io.exception.ExceptionCode
import java.security.Key
import java.util.*
import java.util.stream.Collectors

@Component
class JwtProvider(private val customUserDetailsService: CustomUserDetailsService) {
    companion object {
        private const val HeaderAuthenticationVariableName = "Authorization"
        private const val AUTHORITIES_KEY = "auth"
        private const val BEARER_TYPE = "bearer "
        private const val ACCESS_TOKEN_EXPIRE_TIME = (1000 * 60 * 30) //30분
        private const val REFRESH_TOKEN_EXPIRE_TIME = (1000 * 60 * 60 * 24 * 7) // 7일
        private const val SECRET_KEY = "aXQtaXMtcGhjYWZlLXByb2plY3QtYXBwLWFwaS1zZWNyZXQta2V5" //it-is-phcafe-project-app-api-secret-key
    }

    private val key: Key by lazy {
        val secretKey: String = SECRET_KEY // base64Encoded
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }

    //JWT 토큰 생성
    fun createJwtToken(user: UserEntity, authentication: Authentication): JwtDto.Response {

        //권한들 가져오기
        val authorities = authentication.authorities.stream()
            .map { it.authority }
            .collect(Collectors.joining(","))

        val now = Date()

        val accessToken = Jwts.builder()
            .setClaims(mutableMapOf(AUTHORITIES_KEY to authorities))
            .setSubject(user.id.toString())
            .setIssuedAt(now)
            .setExpiration(Date(now.time + ACCESS_TOKEN_EXPIRE_TIME))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        val refreshToken = Jwts.builder()
            .setClaims(mutableMapOf(AUTHORITIES_KEY to authorities))
            .setSubject(user.id.toString())
            .setIssuedAt(now)
            .setExpiration(Date(now.time + REFRESH_TOKEN_EXPIRE_TIME))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        return JwtDto.Response(
            grantType = BEARER_TYPE,
            accessToken = accessToken,
            refreshToken = refreshToken,
            accessTokenExpiresIn = Date(now.time + ACCESS_TOKEN_EXPIRE_TIME).time,
        )
    }


    //Request에서 Header에 포함된 token을 가져온다. ex: "Authorization" : "Bearer token값"
    fun getTokenInHeader(request: HttpServletRequest): String? {
        return request.getHeader(HeaderAuthenticationVariableName)?.let {
            if (it.startsWith(BEARER_TYPE, true)) {
                it.substring(BEARER_TYPE.length)
            } else {
                null
            }
        }
    }

    //유저의 아이디로 인증정보를 조회하여 userDetail과 userEntity를 반환한다.
    fun getAuthentication(token: String): Pair<UsernamePasswordAuthenticationToken, UserEntity> {
        val userDetail = customUserDetailsService.loadUserById(getUserIdFromJwt(token))

        //redis 대신 로그아웃을 위해 DB에서 체크하도록 한다.
        if(!userDetail.getUser().isOnLine) {
            throw BaseException(ExceptionCode.USER_LOGOUT)
        }

        return Pair(UsernamePasswordAuthenticationToken(userDetail, "", userDetail.authorities), userDetail.getUser())
    }

    //JWT 토큰에서 유저의 ID를 조회한다.
    fun getUserIdFromJwt(token: String): Long {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
                .subject
                .toLong()
    }




}