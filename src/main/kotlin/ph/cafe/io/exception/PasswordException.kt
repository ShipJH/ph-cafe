package ph.cafe.io.exception

class PasswordException(
    override val exceptionCode: ExceptionCode,
): BaseException(exceptionCode)