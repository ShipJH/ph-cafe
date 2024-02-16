package ph.cafe.io.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ph.cafe.io.config.security.jwt.JwtProvider

@Component
class AuthenticationFilter(private val jwtProvider: JwtProvider): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val token = jwtProvider.getTokenInHeader(request)
        token?.let {
            SecurityContextHolder.getContext().authentication = jwtProvider.getAuthentication(token).first
        }

        filterChain.doFilter(request, response);
    }


}