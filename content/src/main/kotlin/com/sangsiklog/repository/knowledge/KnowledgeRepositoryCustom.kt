package com.sangsiklog.repository.knowledge

import com.sangsiklog.domain.knowledge.Knowledge
import java.util.Optional

interface KnowledgeRepositoryCustom {
    fun findPopularKnowledgeByLikes(): List<Knowledge>

    fun findDailyKnowledge(): Optional<Knowledge>
}