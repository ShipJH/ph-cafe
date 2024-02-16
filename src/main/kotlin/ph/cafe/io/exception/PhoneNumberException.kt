package ph.cafe.io.exception

class PhoneNumberException(
    override val exceptionCode: ExceptionCode,
): BaseException(exceptionCode)