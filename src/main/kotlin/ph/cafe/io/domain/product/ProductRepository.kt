package ph.cafe.io.domain.product

import org.springframework.data.jpa.repository.JpaRepository
import ph.cafe.io.domain.product.customRepository.ProductRepositoryCustom
import ph.cafe.io.domain.product.model.ProductEntity

interface ProductRepository: JpaRepository<ProductEntity, Long>, ProductRepositoryCustom {
}