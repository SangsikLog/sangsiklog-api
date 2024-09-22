package com.sangsiklog.repository.knowledge

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sangsiklog.domain.knowledge.Knowledge
import com.sangsiklog.domain.knowledge.QKnowledge
import com.sangsiklog.domain.like.QLike

class KnowledgeRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
): KnowledgeRepositoryCustom {
    override fun findPopularKnowledgeByLikes(): List<Knowledge> {
        val knowledge = QKnowledge.knowledge
        val like = QLike.like

        return jpaQueryFactory
            .select(knowledge)
            .from(knowledge)
            .leftJoin(like).on(like.knowledgeId.eq(knowledge.id))
            .groupBy(knowledge.id)
            .orderBy(like.count().desc())
            .limit(10)
            .fetch()
    }

}