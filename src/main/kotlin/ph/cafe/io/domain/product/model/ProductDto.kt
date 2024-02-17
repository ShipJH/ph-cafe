package ph.cafe.io.domain.product.model

import jakarta.validation.constraints.NotBlank
import org.jetbrains.annotations.NotNull
import ph.cafe.io.domain.user.model.UserEntity
import java.time.LocalDateTime

class ProductDto {

    data class Response(
        val id: Long,
        val category: ProductEnum.Category,
        val price: Int,
        val cost: Int,
        val name: String,
        val description: String,
        val barcode: String,
        val expirationDate: LocalDateTime,
        val size: ProductEnum.Size
    )

    data class SaveRequest(
        @field:NotNull val category: ProductEnum.Category,
        @field:NotNull val price: Int,
        @field:NotNull val cost: Int,
        @field:NotBlank val name: String,
        @field:NotBlank val description: String,
        @field:NotBlank val barcode: String,
        @field:NotNull val expirationDate: LocalDateTime,
        @field:NotNull val size: ProductEnum.Size
    ) {
        fun toEntity(userEntity: UserEntity) = ProductEntity(
            category = category,
            price = price,
            cost = cost,
            name = name,
            description = description,
            barcode = barcode,
            expirationDate = expirationDate,
            size = size,
            user = userEntity
        )
    }

    data class UpdateRequest(
        val id: Long,
        val category: ProductEnum.Category?,
        val price: Int?,
        val cost: Int?,
        val name: String?,
        val description: String?,
        val barcode: String?,
        val expirationDate: LocalDateTime?,
        val size: ProductEnum.Size?
    ) {
        fun toEntity(productEntity: ProductEntity): ProductEntity {
            val updateProductEntity = ProductEntity(
                category = this.category ?: productEntity.category,
                price = this.price ?: productEntity.price,
                cost = this.cost ?: productEntity.cost,
                name = this.name ?: productEntity.name,
                description = this.description ?: productEntity.description,
                barcode = this.barcode ?: productEntity.barcode,
                expirationDate = this.expirationDate ?: productEntity.expirationDate,
                size = this.size ?: productEntity.size,
                user = productEntity.user
            )
            updateProductEntity.id = productEntity.id
            return updateProductEntity
        }
    }



}