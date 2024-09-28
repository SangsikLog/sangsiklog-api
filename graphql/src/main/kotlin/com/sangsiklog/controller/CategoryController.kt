package com.sangsiklog.controller

import com.sangsiklog.model.category.CategoryKnowledgeStatisticGetResponse
import com.sangsiklog.model.category.CategoryListGetResponse
import com.sangsiklog.service.category.CategoryService
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class CategoryController(
    private val categoryService: CategoryService
) {
    @QueryMapping
    suspend fun getCategoryList(): CategoryListGetResponse {
        return categoryService.getCategoryList()
    }

    @QueryMapping
    suspend fun getCategoryKnowledgeStatistic(): CategoryKnowledgeStatisticGetResponse {
        return categoryService.getCategoryKnowledgeStatistic()
    }
}