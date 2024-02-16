package ph.cafe.io.handler

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
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
import ph.cafe.io.common.ResponseDto
import ph.cafe.io.exception.BaseException
import ph.cafe.io.exception.PasswordException
import ph.cafe.io.exception.PhoneNumberException

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
        val message = when (val cause = ex.cause) {
            is MismatchedInputException -> {
                cause.path.joinToString(",") { it.fieldName }
                    .let { "필수값이 누락되었습니다. 필수값을 확인해주세요. [$it]" }
            }
            else -> "예기치 않은 오류 ${cause?.javaClass?.name} ${ex.localizedMessage}"
        }
        val body = ResponseDto.Response(
            meta = ResponseDto.Meta(
                code = HttpStatus.BAD_REQUEST.value(),
                message = message
            ),
            data = null
        )
        return handleExceptionInternal(ex, body, headers, status, request)
    }

//    @ExceptionHandler(BaseException::class)
//    fun handle(ex: BaseException): ResponseEntity<Any> {
//        val apiError = ApiError(
//            status = ex.exceptionCode.status,
//            statusCode = ex.exceptionCode.status.value(),
//            errorType = ex.messageType,
//            errorMessage = ex.message,
//        )
//        return ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
//    }
//
//
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


}