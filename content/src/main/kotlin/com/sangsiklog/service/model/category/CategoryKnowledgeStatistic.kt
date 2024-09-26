package com.sangsiklog.service.model.category

import com.sangsiklog.domain.category.Category
import com.sangsiklog.service.category.CategoryServiceOuterClass

class CategoryKnowledgeStatistic(
    val categoryId: Long = 0,
    val knowledgeCount: Long = 0
) {
    companion object {
        fun from(categoryKnowledgePair: Pair<Long, Long>): CategoryKnowledgeStatistic {
            return CategoryKnowledgeStatistic(
                categoryId = categoryKnowledgePair.first,
                knowledgeCount = categoryKnowledgePair.second
            )
        }
    }

    fun toProto(): CategoryServiceOuterClass.CategoryKnowledgeStatistic {
        return CategoryServiceOuterClass.CategoryKnowledgeStatistic.newBuilder()
            .setCategoryId(this.categoryId)
            .setKnowledgeCount(this.knowledgeCount)
            .build()
    }
}