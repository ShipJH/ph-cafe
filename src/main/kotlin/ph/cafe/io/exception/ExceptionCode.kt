package ph.cafe.io.exception

import org.springframework.http.HttpStatus


enum class ExceptionCode(
    val status: HttpStatus,
    val msg: String
) {
    // Base
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR"),

    //password
    PASSWORD_EMPTY(HttpStatus.BAD_REQUEST, "비밀번호가 비어있습니다."),
    PASSWORD_LENGTH_NOT_VALID(HttpStatus.BAD_REQUEST, "비밀번호는 8자 이상이어야 합니다."),
    PASSWORD_NUMBER_NOT_VALID(HttpStatus.BAD_REQUEST, "비밀번호는 숫자를 포함해야 합니다."),
    PASSWORD_STRING_UPPER_NOT_VALID(HttpStatus.BAD_REQUEST, "비밀번호는 대문자 영문을 포함해야 합니다."),
    PASSWORD_STRING_LOWER_NOT_VALID(HttpStatus.BAD_REQUEST, "비밀번호는 소문자 영문을 포함해야 합니다."),
    PASSWORD_SPECIAL_CHARACTER_NOT_VALID(HttpStatus.BAD_REQUEST, "비밀번호는 특수문자를 포함해야 합니다."),

    
}