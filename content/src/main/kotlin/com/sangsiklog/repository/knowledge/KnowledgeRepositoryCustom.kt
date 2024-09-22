package com.sangsiklog.repository.knowledge

import com.sangsiklog.domain.knowledge.Knowledge

interface KnowledgeRepositoryCustom {
    fun findPopularKnowledgeByLikes(): List<Knowledge>
}