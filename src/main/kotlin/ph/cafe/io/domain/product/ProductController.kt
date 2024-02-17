package ph.cafe.io.domain.product

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ph.cafe.io.common.ResponseDto
import ph.cafe.io.domain.product.model.ProductDto

@RestController
class ProductController(
    private val productService: ProductService
) {

    @GetMapping("/product")
    fun getProduct(): String {
        return "product"
    }

    @GetMapping("/product/{id}")
    fun getProductById(@PathVariable("id") productId: Long): String {
        return "product"
    }

    @PostMapping("/product")
    fun saveProduct(@RequestBody @Valid request: ProductDto.SaveRequest): ResponseDto.Response {
        return productService.saveProduct(request)
    }

    @PutMapping("/product")
    fun updateProduct(@RequestBody @Valid request: ProductDto.UpdateRequest): ResponseDto.Response {
        return productService.updateProduct(request)
    }

    @DeleteMapping("/product/{id}")
    fun deleteProduct(@PathVariable("id") productId: Long): ResponseDto.Response {
        return productService.deleteProduct(productId)
    }

}