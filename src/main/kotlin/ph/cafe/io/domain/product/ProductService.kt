package ph.cafe.io.domain.product

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ph.cafe.io.common.ResponseDto
import ph.cafe.io.domain.product.model.ProductDto
import ph.cafe.io.domain.user.UserEntityGetService
import ph.cafe.io.exception.BaseException
import ph.cafe.io.exception.ExceptionCode
import ph.cafe.io.utils.StringUtils
import java.sql.SQLException
import java.util.NoSuchElementException

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val userEntityGetService: UserEntityGetService
) {

    fun getProductList(request: ProductDto.Request): ResponseDto.Response {
        try {
            val userEntity = userEntityGetService.getUserEntity()
            val productEntityList = productRepository.findProductListByUserAndRequest(userEntity.id, request)
            return ResponseDto.Response(
                ResponseDto.Meta(
                    code = 200,
                    message = "상품 조회 성공"
                ),
                data = ProductDto.ListResponse(
                    products = productEntityList.content.map { it.toResponse() }.toMutableList(),
                    isNextPage = productEntityList.hasNext(),
                    lastId = productEntityList.content.last().id
                )
            )
        } catch (e: SQLException) {
            throw BaseException(ExceptionCode.SQL_ERROR)
        } catch (e: NoSuchElementException) {
            throw BaseException(ExceptionCode.NOT_FOUND)
        } catch (e: Exception) {
            throw BaseException(ExceptionCode.NOT_FOUND)
        }
    }

    fun getProductById(productId: Long): ResponseDto.Response {
        try {
            val productEntity = productRepository.findById(productId)
                .orElseThrow { throw BaseException(ExceptionCode.NOT_FOUND) }
            userEntityGetService.loginUserCheck(productEntity.user.id)
            return ResponseDto.Response(
                ResponseDto.Meta(
                    code = 200,
                    message = "상품 조회 성공"
                ),
                data = productEntity.toResponse()
            )
        } catch (e: SQLException) {
            throw BaseException(ExceptionCode.SQL_ERROR)
        } catch (e: BaseException) {
            throw e
        } catch (e: Exception) {
            throw BaseException(ExceptionCode.NOT_FOUND)
        }
    }



    @Transactional
    fun saveProduct(request: ProductDto.SaveRequest): ResponseDto.Response {
        try {
            val userEntity = userEntityGetService.getUserEntity()
            val productEntity = request.toEntity(userEntity)
            productRepository.save(productEntity)
            return ResponseDto.Response(
                ResponseDto.Meta(
                    code = 200,
                    message = "상품 등록 성공"
                ),
                data = productEntity.toResponse()
            )
        } catch (e: SQLException) {
            throw BaseException(ExceptionCode.SQL_ERROR)
        } catch (e: Exception) {
            throw BaseException(ExceptionCode.SAVE_FAIL)
        }
    }

    @Transactional
    fun updateProduct(request: ProductDto.UpdateRequest): ResponseDto.Response {
        try {
            val productEntity = productRepository.findById(request.id)
                .orElseThrow { throw BaseException(ExceptionCode.NOT_FOUND) }
            userEntityGetService.loginUserCheck(productEntity.user.id)
            val updateProductEntity = request.toEntity(productEntity)
            productRepository.save(updateProductEntity)
            return ResponseDto.Response(
                ResponseDto.Meta(
                    code = 200,
                    message = "상품 수정 성공"
                ),
                data = updateProductEntity.toResponse()
            )
        } catch (e: SQLException) {
            throw BaseException(ExceptionCode.SQL_ERROR)
        } catch (e: BaseException) {
            throw e
        }catch (e: Exception) {
            throw BaseException(ExceptionCode.UPDATE_FAIL)
        }
    }

    @Transactional
    fun deleteProduct(productId: Long): ResponseDto.Response {
        try {
            val productEntity = productRepository.findById(productId)
                .orElseThrow { throw BaseException(ExceptionCode.NOT_FOUND) }
            userEntityGetService.loginUserCheck(productEntity.user.id)
            productRepository.delete(productEntity)
            return ResponseDto.Response(
                ResponseDto.Meta(
                    code = 200,
                    message = "상품 삭제 성공"
                ),
                data = productEntity.toResponse()
            )
        } catch (e: SQLException) {
            throw BaseException(ExceptionCode.SQL_ERROR)
        } catch (e: BaseException) {
            throw e
        } catch (e: Exception) {
            throw BaseException(ExceptionCode.DELETE_FAIL)
        }
    }




}