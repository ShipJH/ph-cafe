package ph.cafe.io.config

import com.querydsl.jpa.JPQLTemplates
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext

@Configuration
class JpaQueryDslFactory {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    @Bean
    fun jpaQueryFactory(): JPAQueryFactory {
        return JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager)
    }
}