package com.sangsiklog.service.category

import com.google.protobuf.Empty
import com.sangsiklog.config.GrpcProperties
import com.sangsiklog.core.grpc.GrpcClient
import com.sangsiklog.model.category.CategoryListGetResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class CategoryService(
    grpcClient: GrpcClient,
    grpcProperties: GrpcProperties
) {
    private val categoryServiceStub = grpcClient.createStub(
        stubClass = CategoryServiceGrpcKt.CategoryServiceCoroutineStub::class,
        serviceName = grpcProperties.contentApi.serviceName,
        port = grpcProperties.contentApi.port
    )

    suspend fun getCategoryList(): CategoryListGetResponse {
        return withContext(Dispatchers.IO) {
            val response = categoryServiceStub.getCategoryList(Empty.getDefaultInstance())

            CategoryListGetResponse.fromProto(response)
        }
    }
}