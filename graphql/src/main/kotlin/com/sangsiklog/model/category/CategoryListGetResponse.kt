package com.sangsiklog.model.category

import com.sangsiklog.service.category.CategoryServiceOuterClass

class CategoryListGetResponse(
    val categoryList: List<Category> = emptyList()
)  {
    companion object {
        fun fromProto(proto: CategoryServiceOuterClass.CategoryListGetResponse): CategoryListGetResponse {
            val categoryList = proto.categoryDetailList
                .map { Category.fromProto(it) }
                .toList()

            return CategoryListGetResponse(
                categoryList = categoryList
            )
        }
    }
}