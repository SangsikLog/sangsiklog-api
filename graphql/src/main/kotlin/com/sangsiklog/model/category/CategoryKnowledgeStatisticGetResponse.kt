package com.sangsiklog.model.category

import com.sangsiklog.service.category.CategoryServiceOuterClass

class CategoryKnowledgeStatisticGetResponse(
    val statistic: List<CategoryKnowledgeStatistic> = emptyList()
)  {
    companion object {
        fun fromProto(proto: CategoryServiceOuterClass.CategoryKnowledgeStatisticGetResponse): CategoryKnowledgeStatisticGetResponse {
            val statistic = proto.categoryKnowledgeStatisticList
                .map { CategoryKnowledgeStatistic.fromProto(it) }
                .toList()

            return CategoryKnowledgeStatisticGetResponse(
                statistic = statistic
            )
        }
    }
}