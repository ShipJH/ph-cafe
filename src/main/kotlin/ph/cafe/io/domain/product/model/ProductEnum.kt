package ph.cafe.io.domain.product.model

class ProductEnum {

    enum class Category(val value: String) {
        COFFEE("커피"),
        TEA("차"),
        BAKERY("빵"),
        DESSERT("디저트"),
        FOOD("음식"),
        BEVERAGE("음료"),
        ETC("기타")
    }

    enum class Size(val value: String) {
        SMALL("소"),
        LARGE("대")
    }
}