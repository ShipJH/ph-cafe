package ph.cafe.io.exception

open class BaseException(
    open val exceptionCode: ExceptionCode
): RuntimeException() {
    constructor(): this(ExceptionCode.INTERNAL_SERVER_ERROR)
    override val message: String?
        get() = exceptionCode.msg

    val messageType: String
        get() = exceptionCode.name

}