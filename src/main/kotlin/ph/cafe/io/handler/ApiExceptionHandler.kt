package ph.cafe.io.handler

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ph.cafe.io.common.Constants.COMMA
import ph.cafe.io.common.Constants.EMPTY
import ph.cafe.io.common.ResponseDto
import ph.cafe.io.exception.BaseException
import ph.cafe.io.exception.ExceptionCode
import ph.cafe.io.exception.PasswordException
import ph.cafe.io.exception.PhoneNumberException
import java.time.format.DateTimeParseException
import java.util.regex.Matcher
import java.util.regex.Pattern

@ControllerAdvice
class ApiExceptionHandler: ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val data = mutableListOf<String>()
        ex.bindingResult.fieldErrors.forEach { data.plus("${it.field}: ${it.defaultMessage}") }
        ex.bindingResult.globalErrors.forEach { data.plus("${it.objectName}: ${it.defaultMessage}") }
        val body = ResponseDto.Response(
            meta = ResponseDto.Meta(
                code = HttpStatus.BAD_REQUEST.value(),
                message = HttpStatus.BAD_REQUEST.value().toString()
            ),
            data = data
        )
        return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        var message = EMPTY
        var data = EMPTY
        when (val cause = ex.cause) {
            is MismatchedInputException -> {
                message = cause.path.joinToString(COMMA) { it.fieldName }
                    .let { "${ExceptionCode.NOT_VALID.msg} [$it]" }

                if(cause.message != null && cause.message!!.contains(("Enum class"))) {
                    val pattern = Pattern.compile("\\[(.*?)\\]")
                    val matcher: Matcher = pattern.matcher(cause.message!!)
                    if (matcher.find()) {
                        val match = matcher.group(1)
                        data = match
                    }
                }
            }
            is JsonMappingException -> {
                message = if(cause.cause is DateTimeParseException) {
                    ExceptionCode.NOT_VALID_FORMAT_LDT.msg
                } else {
                    ExceptionCode.NOT_VALID_JSON.msg
                }
            }
            else -> {
                message = "예기치 않은 오류 ${cause?.javaClass?.name} ${ex.localizedMessage}"
            }
        }

        val body = ResponseDto.Response(
            meta = ResponseDto.Meta(
                code = HttpStatus.BAD_REQUEST.value(),
                message = message
            ),
            data = if(data == EMPTY) null else data
        )
        return handleExceptionInternal(ex, body, headers, status, request)
    }

    @ExceptionHandler(PasswordException::class)
    fun handle(ex: PasswordException): ResponseEntity<Any> {
        val body = ResponseDto.Response(
            meta = ResponseDto.Meta(
                code = ex.exceptionCode.status.value(),
                message = ex.exceptionCode.msg
            ),
            data = null
        )
        return ResponseEntity(body, ex.exceptionCode.status)
    }

    @ExceptionHandler(PhoneNumberException::class)
    fun handle(ex: PhoneNumberException): ResponseEntity<Any> {
        val body = ResponseDto.Response(
            meta = ResponseDto.Meta(
                code = ex.exceptionCode.status.value(),
                message = ex.exceptionCode.msg
            ),
            data = null
        )
        return ResponseEntity(body, ex.exceptionCode.status)
    }

    @ExceptionHandler(BaseException::class)
    fun handle(ex: BaseException): ResponseEntity<Any> {
        val body = ResponseDto.Response(
            meta = ResponseDto.Meta(
                code = ex.exceptionCode.status.value(),
                message = ex.exceptionCode.msg
            ),
            data = null
        )
        return ResponseEntity(body, ex.exceptionCode.status)
    }

}