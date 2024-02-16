package ph.cafe.io.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import ph.cafe.io.common.ResponseDto
import ph.cafe.io.exception.BaseException
import ph.cafe.io.exception.ExceptionCode
import java.io.IOException

class ExceptionHandlerFilter: OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: ExpiredJwtException) {
            setErrorResponse(response, ExceptionCode.EXPIRED_TOKEN) //토큰의 유효기간 만료
        } catch (e: SecurityException) {
            setErrorResponse(response, ExceptionCode.INVALID_TOKEN)
        } catch (e: UnsupportedJwtException) {
            setErrorResponse(response, ExceptionCode.INVALID_TOKEN)
        } catch (e: MalformedJwtException) {
            setErrorResponse(response, ExceptionCode.INVALID_TOKEN)
        } catch (e: JwtException) {
            setErrorResponse(response, ExceptionCode.INVALID_TOKEN)
        } catch (e: IllegalArgumentException) {
            setErrorResponse(response, ExceptionCode.INVALID_TOKEN)
        } catch (e: BaseException) {
            setErrorResponse(response, e.exceptionCode)
        } catch (e: Exception) {
            setErrorResponse(response, ExceptionCode.INTERNAL_SERVER_ERROR)
        }
    }

    private fun setErrorResponse(
        response: HttpServletResponse,
        errorCode: ExceptionCode
    ) {
        val objectMapper = ObjectMapper()
        response.status = errorCode.status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        //에러메세지 한글화

        val body = ResponseDto.Response(
            meta = ResponseDto.Meta(
                code = errorCode.status.value(),
                message = errorCode.msg
            ),
            data = null
        )

        try {
            response.writer.write(objectMapper.writeValueAsString(body))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

