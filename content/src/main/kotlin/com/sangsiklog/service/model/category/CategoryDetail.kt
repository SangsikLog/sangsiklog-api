package com.sangsiklog.service.model.category

import com.sangsiklog.domain.category.Category
import com.sangsiklog.service.category.CategoryServiceOuterClass

class CategoryDetail(
    val id: Long = 0,
    val name: String? = null
) {
    companion object {
        fun from(category: Category): CategoryDetail {
            return CategoryDetail(
                id = category.id!!,
                name = category.name
            )
        }
    }

    fun toProto(): CategoryServiceOuterClass.CategoryDetail {
        return CategoryServiceOuterClass.CategoryDetail.newBuilder()
            .setCategoryId(this.id)
            .setName(this.name)
            .build()
    }
}