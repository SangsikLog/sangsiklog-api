package com.sangsiklog.repository.category

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sangsiklog.domain.category.QCategory
import com.sangsiklog.domain.knowledge.QKnowledge

class CategoryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
): CategoryRepositoryCustom {
    override fun findAllCategoryWithKnowledgeCount(): List<Pair<Long, Long>> {
        val category = QCategory.category
        val knowledge = QKnowledge.knowledge

        val query = jpaQueryFactory
            .select(category.id, knowledge.count())
            .from(category)
            .leftJoin(category.knowledgeList, knowledge)
            .groupBy(category.id)

        return query.fetch()
            .map { tuple -> Pair(tuple.get(0, Long::class.java)!! , tuple.get(1, Long::class.java)!!) }
    }
}