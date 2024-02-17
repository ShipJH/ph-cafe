package ph.cafe.io.common


open class BaseListRequest {
    var size: Int = 10
    var lastId: Long? = null
//
//    fun getPageRequest(): PageRequest {
//        return PageRequest.of(getPaging(), size)
//    }
}