package ph.cafe.io

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import ph.cafe.io.domain.product.ProductController
import ph.cafe.io.domain.product.ProductRepository
import ph.cafe.io.domain.product.ProductService
import ph.cafe.io.domain.product.model.ProductEntity
import ph.cafe.io.domain.product.model.ProductEnum
import ph.cafe.io.domain.user.model.UserEntity
import java.time.LocalDateTime
import java.util.*

@WebMvcTest(ProductController::class)
internal class ProductTest: BehaviorSpec() {

    init {
        val productRepository = mockk<ProductRepository>()
        val productService = ProductService(productRepository, mockk())

        afterContainer {
            clearAllMocks()
        }

        Given("상품 등록") {

            every { productRepository.save(productEntity) } returns productEntity
            every { productRepository.count() } returns 1

            When("상품 등록 요청") {
                Then("상품 등록 성공") {
                    withContext(Dispatchers.IO) {
                        productRepository.save(productEntity)
                    } shouldBe productEntity
                }
                Then("상품 등록 확인 - 이름") {
                    withContext(Dispatchers.IO) {
                        productRepository.save(productEntity)
                    }.name shouldBe "아메리카노"
                }
                Then("상품 등록 확인 - 가격") {
                    withContext(Dispatchers.IO) {
                        productRepository.save(productEntity)
                    }.price shouldBe 3000
                }
                Then("1행이 등록 된다.") {
                    1 shouldBe withContext(Dispatchers.IO) {
                        productRepository.count()
                    }
                }
            }
        }


        Given("상품 수정") {

            every { productRepository.findById(1) } returns Optional.of(productEntity)
            every { productRepository.save(productEntity) } returns updateProductEntity

            When("상품 수정 요청") {
                Then("상품 등록 성공") {
                    productEntity.name = "디카페인 아메리카노"
                    productEntity.price = 5500
                    withContext(Dispatchers.IO) {
                        productRepository.save(productEntity)
                    } shouldBe updateProductEntity
                }
                Then("상품 수정 확인 - 이름") {
                    withContext(Dispatchers.IO) {
                        productRepository.save(productEntity)
                    }.name shouldBe "디카페인 아메리카노"
                }
                Then("상품 수정 확인 - 가격") {
                    withContext(Dispatchers.IO) {
                        productRepository.save(productEntity)
                    }.price shouldBe 5500
                }
            }
        }


    }

    companion object {
        val productEntity = ProductEntity(
            category = ProductEnum.Category.COFFEE,
            price = 3000,
            cost = 1000,
            name = "아메리카노",
            description = "아메리카노",
            barcode = "1234567890",
            expirationDate = LocalDateTime.now(),
            size = ProductEnum.Size.SMALL,
            user = UserEntity(
                phoneNumber = "01055002890",
                password = "1234",
            )
        )

        val updateProductEntity = ProductEntity(
            category = ProductEnum.Category.COFFEE,
            price = 5500,
            cost = 2000,
            name = "디카페인 아메리카노",
            description = "스페셜 디카페인 아메리카노 라지",
            barcode = "888777666555444",
            expirationDate = LocalDateTime.now(),
            size = ProductEnum.Size.LARGE,
            user = UserEntity(
                phoneNumber = "01055002890",
                password = "1234",
            )
        )
    }


}