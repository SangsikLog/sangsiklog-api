package com.sangsiklog.service.category

import com.google.protobuf.Empty
import com.sangsiklog.repository.category.CategoryRepository
import com.sangsiklog.service.model.category.CategoryDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CategoryService(
    private val categoryRepository: CategoryRepository
): CategoryServiceGrpcKt.CategoryServiceCoroutineImplBase() {

    override suspend fun getCategoryList(request: Empty): CategoryServiceOuterClass.CategoryListGetResponse {
        return withContext(Dispatchers.IO) {
            val categoryDetailList = categoryRepository.findAll()
                .map { CategoryDetail.from(it).toProto() }
                .toList()

            CategoryServiceOuterClass.CategoryListGetResponse.newBuilder()
                .addAllCategoryDetail(categoryDetailList)
                .build()
        }
    }
}