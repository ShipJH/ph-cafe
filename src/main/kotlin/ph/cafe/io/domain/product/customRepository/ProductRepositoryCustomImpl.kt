package ph.cafe.io.domain.product.customRepository

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import ph.cafe.io.common.BaseEnum
import ph.cafe.io.common.Constants.EMPTY
import ph.cafe.io.common.Constants.SPACE
import ph.cafe.io.domain.product.model.ProductDto
import ph.cafe.io.domain.product.model.ProductEntity
import ph.cafe.io.domain.product.model.QProductEntity
import ph.cafe.io.utils.StringUtils

class ProductRepositoryCustomImpl(
    private val jpaQueryFactory: JPAQueryFactory
): ProductRepositoryCustom {


    override fun findProductListByUserAndRequest(userId: Long, request: ProductDto.Request): Page<ProductEntity> {
        val productEntity = QProductEntity.productEntity

        val query = jpaQueryFactory
            .selectFrom(productEntity)
            .where(
                productEntity.user.id.eq(userId),
                lastIdCondition(request.lastId),
                nameCondition(request)

            )
            .orderBy(productEntity.id.desc())
            .limit(request.size.toLong())

        val queryCount = jpaQueryFactory
            .select(productEntity.id.count())
            .from(productEntity)
            .where(
                productEntity.user.id.eq(userId),
                lastIdCondition(request.lastId),
                nameCondition(request)
            )


        val result = query.fetch()
        val totalElements = queryCount.fetchOne() as Long

        return PageImpl(result, Pageable.ofSize(request.size), totalElements)
    }


    private fun lastIdCondition(lastId: Long?): BooleanExpression? {
        return if(lastId != null) {
            QProductEntity.productEntity.id.lt(lastId)
        } else {
            null
        }
    }

    private fun nameCondition(request: ProductDto.Request): BooleanExpression? {
        return if(request.name != null) {
            when(request.nameType()) {
                BaseEnum.Language.KOREAN_INITIAL -> QProductEntity.productEntity.initial.contains(request.deleteSpaceName())
                BaseEnum.Language.KOREAN -> {
                    val name = StringUtils.makeInitial(request.name)
                    QProductEntity.productEntity.initial.contains(name)
                }
                else -> QProductEntity.productEntity.name.contains(request.name)
            }
        } else {
            null
        }
    }

}