package ph.cafe.io

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import ph.cafe.io.common.BaseEnum
import ph.cafe.io.domain.product.ProductController
import ph.cafe.io.domain.product.ProductRepository
import ph.cafe.io.domain.product.model.ProductDto
import ph.cafe.io.domain.product.model.ProductEntity
import ph.cafe.io.domain.product.model.ProductEnum
import ph.cafe.io.domain.user.model.UserEntity
import ph.cafe.io.utils.StringUtils
import java.time.LocalDateTime
import java.util.*

@WebMvcTest(ProductController::class)
internal class ProductTest: BehaviorSpec() {

    init {
        val productRepository = mockk<ProductRepository>()

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
                    }.name shouldBe updateProductEntity.name
                }
                Then("상품 수정 확인 - 가격") {
                    withContext(Dispatchers.IO) {
                        productRepository.save(productEntity)
                    }.price shouldBe updateProductEntity.price
                }
            }
        }

        Given("상품 삭제") {

            every { productRepository.findById(1) } returns Optional.of(productEntity)
            every { productRepository.delete(productEntity) } returns Unit

            When("상품 삭제 요청") {
                Then("상품 삭제 성공") {
                    withContext(Dispatchers.IO) {
                        productRepository.delete(productEntity)
                    } shouldBe Unit
                }
            }
        }


        Given("상품명 초성변경 (한글)") {
            val name = "아메리카노"
            When("한국어 초성으로 변경하면") {
                val transferString = StringUtils.makeInitial(name)
                Then("초성으로 변경되는지 확인") {
                    transferString shouldBe "ㅇㅁㄹㅋㄴ"
                }
            }
        }

        Given("상품명 초성변경 (한글 - 띄어쓰기)") {
            val name = "아이스 아메리카노"
            When("한국어 초성으로 변경하면") {
                val transferString = StringUtils.makeInitial(name)
                Then("공백없는 초성으로 변경되는지 확인") {
                    transferString shouldBe "ㅇㅇㅅㅇㅁㄹㅋㄴ"
                }
            }
        }

        Given("상품명 초성변경 (영어)") {
            val name = "ICE AMERICANO"
            When("영어를 초성으로 변경하면") {
                val transferString = StringUtils.makeInitial(name)
                Then("영어 그대로 나오는지 확인") {
                    transferString shouldBe "ICE AMERICANO"
                }
            }
        }

        Given("상품명 초성 변경 (영어+한글)") {
            val name = "아이스 아메리카노 SAMLL"
            When("영어+한글을 초성으로 변경하면") {
                val transferString = StringUtils.makeInitial(name)
                Then("SMALL이(엉어가) 없는 초성으로 나오는지 확인") {
                    transferString shouldBe "ㅇㅇㅅㅇㅁㄹㅋㄴ"
                }
            }
        }

        Given("상품명 초성 변경 (숫자)") {
            val name = "1"
            When("숫자로 변경하면") {
                val transferString = StringUtils.makeInitial(name)
                Then("숫자 그대로 나오는지 확인") {
                    transferString shouldBe "1"
                }
            }
        }

        Given("상품명의 리퀘스트 타입을 확인 (한국어)") {
            val request = ProductDto.Request(
                name = "아메리카노"
            )
            When("리퀘스트 타입이 한국어인지 확인") {
                val type = request.nameType()

                Then("KOREAN 타입 확인") {
                    type shouldBe BaseEnum.Language.KOREAN
                }
            }
        }

        Given("상품명의 리퀘스트 타입을 확인 (영어)") {
            val request = ProductDto.Request(
                name = "ICE AMERICANO"
            )
            When("리퀘스트 타입이 영어인지 확인") {
                val type = request.nameType()

                Then("KOREAN 타입 확인") {
                    type shouldBe BaseEnum.Language.ENGLISH
                }
            }
        }

        Given("상품명의 리퀘스트 타입을 확인 (한국어 초성)") {
            val request = ProductDto.Request(
                name = "ㅇㅁㄹㅋㄴ"
            )
            When("리퀘스트 타입이 한국어 초성인지 확인") {
                val type = request.nameType()

                Then("KOREAN_INITIAL 타입 확인") {
                    type shouldBe BaseEnum.Language.KOREAN_INITIAL
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
            initial = "ㅇㅁㄹㅋㄴ",
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
            initial = "ㄷㅋㅍㅇㄴ",
            user = UserEntity(
                phoneNumber = "01055002890",
                password = "1234",
            )
        )
    }


}