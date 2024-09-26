package com.sangsiklog.model.category

import com.sangsiklog.service.category.CategoryServiceOuterClass

data class CategoryKnowledgeStatistic(
    val categoryId: Long = 0,
    val knowledgeCount: Long = 0
) {
    companion object {
        fun fromProto(proto: CategoryServiceOuterClass.CategoryKnowledgeStatistic): CategoryKnowledgeStatistic {
            return CategoryKnowledgeStatistic(
                categoryId = proto.categoryId,
                knowledgeCount = proto.knowledgeCount
            )
        }
    }
}
