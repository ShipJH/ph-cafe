package ph.cafe.io.domain.product.model

import jakarta.persistence.*
import ph.cafe.io.common.BaseEntity
import ph.cafe.io.domain.user.model.UserEntity
import java.time.LocalDateTime

@Entity
@Table(name = "product")
class ProductEntity(

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    var category: ProductEnum.Category,

    @Column(length = 10, nullable = false)
    var price: Int,

    @Column(length = 10, nullable = false)
    var cost: Int,

    @Column(length = 50, nullable = false)
    var name: String,

    @Column(length = 1000, nullable = false)
    var description: String,

    @Column(length = 100, nullable = false)
    var barcode: String,

    @Column(nullable = false)
    var expirationDate: LocalDateTime,

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    var size: ProductEnum.Size,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: UserEntity

): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    fun toResponse() = ProductDto.Response(
        id = id,
        category = category,
        price = price,
        cost = cost,
        name = name,
        description = description,
        barcode = barcode,
        expirationDate = expirationDate,
        size = size
    )
}