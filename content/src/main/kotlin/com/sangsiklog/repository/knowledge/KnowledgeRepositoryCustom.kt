package com.sangsiklog.repository.knowledge

import com.sangsiklog.domain.category.Category
import com.sangsiklog.domain.knowledge.Knowledge
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.Optional

interface KnowledgeRepositoryCustom {
    fun findPopularKnowledgeByLikes(category: Category?): List<Knowledge>

    fun findDailyKnowledge(): Optional<Knowledge>

    fun findAllByCategory(category: Category?, pageable: Pageable): Page<Knowledge>
}