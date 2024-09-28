package com.sangsiklog.repository.category

interface CategoryRepositoryCustom {
    fun findAllCategoryWithKnowledgeCount(): List<Pair<Long, Long>>
}