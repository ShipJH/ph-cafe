package ph.cafe.io.common

class ResponseDto {

    data class Response(
        val meta: Meta,
        val data: Any?
    )

    data class Meta(
        val code: Int,
        val message: String
    )

}