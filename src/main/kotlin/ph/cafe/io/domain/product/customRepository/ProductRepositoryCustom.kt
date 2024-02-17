package ph.cafe.io.domain.product.customRepository

import org.springframework.data.domain.Page
import ph.cafe.io.domain.product.model.ProductDto
import ph.cafe.io.domain.product.model.ProductEntity

interface ProductRepositoryCustom {

    fun findProductListByUserAndRequest(userId: Long, request: ProductDto.Request): Page<ProductEntity>
}