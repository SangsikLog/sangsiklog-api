package com.sangsiklog.repository.knowledge

import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import com.sangsiklog.domain.category.Category
import com.sangsiklog.domain.knowledge.Knowledge
import com.sangsiklog.domain.knowledge.QKnowledge
import com.sangsiklog.domain.like.QLike
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.*

class KnowledgeRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
): KnowledgeRepositoryCustom {
    override fun findPopularKnowledgeByLikes(category: Category?): List<Knowledge> {
        val knowledge = QKnowledge.knowledge
        val like = QLike.like

        val query = jpaQueryFactory
            .select(knowledge)
            .from(knowledge)
            .leftJoin(like).on(like.knowledgeId.eq(knowledge.id))
            .groupBy(knowledge.id)
            .orderBy(like.count().desc())
            .limit(10)

        if (category != null) {
            query.where(knowledge.category.eq(category))
        }

        return query.fetch()
    }

    override fun findDailyKnowledge(): Optional<Knowledge> {
        val knowledge = QKnowledge.knowledge
        return Optional.ofNullable(
            jpaQueryFactory
                .selectFrom(knowledge)
                .orderBy(Expressions.numberTemplate(Double::class.java, "function('rand')").asc())
                .fetchFirst()
        )
    }

    override fun findAllByCategory(category: Category?, pageable: Pageable): Page<Knowledge> {
        val knowledge = QKnowledge.knowledge

        val query = jpaQueryFactory
            .selectFrom(knowledge)
            .where(
                category?.let { knowledge.category.eq(it) }
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())

        val knowledgeList = query.fetch()

        val total = jpaQueryFactory
            .select(knowledge.count())
            .from(knowledge)
            .where(
                category?.let { knowledge.category.eq(it) }
            )
            .fetchOne()

        return PageImpl(knowledgeList, pageable, total ?: 0)
    }
}