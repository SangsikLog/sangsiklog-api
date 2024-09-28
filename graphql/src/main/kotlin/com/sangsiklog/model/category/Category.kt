package com.sangsiklog.model.category

import com.sangsiklog.service.category.CategoryServiceOuterClass.CategoryDetail

data class Category(
    val id: Long = 0,
    val name: String = ""
) {
    companion object {
        fun fromProto(proto: CategoryDetail): Category {
            return Category(
                id = proto.categoryId,
                name = proto.name
            )
        }
    }
}
