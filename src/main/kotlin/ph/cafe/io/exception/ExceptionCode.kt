package ph.cafe.io.exception

import org.springframework.http.HttpStatus


enum class ExceptionCode(
    val status: HttpStatus,
    val msg: String
) {
    // Base
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예기치 못한 에러입니다."),
    NOT_FOUND(HttpStatus.NO_CONTENT, "데이터를 찾을 수 없습니다."),
    ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "이미 등록된 데이터입니다."),
    SQL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SQL 에러"),
    SAVE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "저장 실패"),
    UPDATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "수정 실패"),
    DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "삭제 실패"),
    NOT_VALID(HttpStatus.BAD_REQUEST, "필수값이 누락되었거나 잘못된 값입니다. 필수값을 확인해주세요."),
    NOT_VALID_FORMAT_LDT(HttpStatus.BAD_REQUEST, "날짜 형식이 잘못되었습니다. [yyyy-MM-dd HH:mm:ss] 형식으로 입력해주세요. 00시~23시 사이의 시간을 입력해주세요."),
    NOT_VALID_JSON(HttpStatus.BAD_REQUEST, "JSON 형식이 잘못되었습니다."),

    //phoneNumber
    PHONE_NUMBER_EMPTY(HttpStatus.BAD_REQUEST, "휴대폰 번호가 비어있습니다."),
    PHONE_NUMBER_NOT_VALID(HttpStatus.BAD_REQUEST, "휴대폰 번호를 확인해주세요. [01x-1234-5678] 형식으로 입력해주세요."),

    //password
    PASSWORD_EMPTY(HttpStatus.BAD_REQUEST, "비밀번호가 비어있습니다."),
    PASSWORD_LENGTH_NOT_VALID(HttpStatus.BAD_REQUEST, "비밀번호는 8자 이상이어야 합니다."),
    PASSWORD_NUMBER_NOT_VALID(HttpStatus.BAD_REQUEST, "비밀번호는 숫자를 포함해야 합니다."),
    PASSWORD_STRING_UPPER_NOT_VALID(HttpStatus.BAD_REQUEST, "비밀번호는 대문자 영문을 포함해야 합니다."),
    PASSWORD_STRING_LOWER_NOT_VALID(HttpStatus.BAD_REQUEST, "비밀번호는 소문자 영문을 포함해야 합니다."),
    PASSWORD_SPECIAL_CHARACTER_NOT_VALID(HttpStatus.BAD_REQUEST, "비밀번호는 특수문자를 포함해야 합니다."),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    //user
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "유저를 찾을 수 없습니다."),
    USER_LOGOUT(HttpStatus.UNAUTHORIZED, "로그아웃 되었습니다."),
    USER_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "유저의 권한이 없습니다."),

    //token
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰의 유효기간이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    NOT_MATCH_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 일치하지 않습니다."),

    
}