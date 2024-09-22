package com.sangsiklog.service.knowlege

import com.google.protobuf.Empty
import com.sangsiklog.config.GrpcProperties
import com.sangsiklog.core.grpc.GrpcClient
import com.sangsiklog.model.SortDirection
import com.sangsiklog.model.knowledge.Knowledge
import com.sangsiklog.model.knowledge.KnowledgeListGetResponse
import com.sangsiklog.model.knowledge.PopularKnowledgeListGetResponse
import com.sangsiklog.service.knowledge.KnowledgeServiceGrpcKt
import com.sangsiklog.service.knowledge.KnowledgeServiceOuterClass.*
import common.Common.Pageable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class KnowledgeService(
    grpcClient: GrpcClient,
    grpcProperties: GrpcProperties
) {
    private val knowledgeServiceStub = grpcClient.createStub(
        stubClass = KnowledgeServiceGrpcKt.KnowledgeServiceCoroutineStub::class,
        serviceName = grpcProperties.contentApi.serviceName,
        port = grpcProperties.contentApi.port
    )

    suspend fun registerKnowledge(userId: Long, title: String, description: String, categoryId: Long): Knowledge {
        return withContext(Dispatchers.IO) {
            val request = KnowledgeRegistrationRequest.newBuilder()
                .setUserId(userId)
                .setTitle(title)
                .setDescription(description)
                .setCategoryId(categoryId)
                .build()

            val response = knowledgeServiceStub.registerKnowledge(request)

            Knowledge.fromProto(response)
        }
    }

    suspend fun getKnowledgeList(page: Int, size: Int, sortColumn: String, direction: SortDirection): KnowledgeListGetResponse {
        return withContext(Dispatchers.IO) {
            val pageable = Pageable.newBuilder()
                .setPage(page)
                .setSize(size)
                .setSortColumn(sortColumn)
                .setDirection(direction.toProto())
                .build()

            val request = KnowledgeListGetRequest.newBuilder()
                .setPageable(pageable)
                .build()

            val response = knowledgeServiceStub.getKnowledgeList(request)

            KnowledgeListGetResponse.fromProto(response)
        }
    }

    suspend fun getKnowledgeDetail(knowledgeId: Long): Knowledge {
        return withContext(Dispatchers.IO) {
            val request = KnowledgeDetailGetRequest.newBuilder()
                .setKnowledgeId(knowledgeId)
                .build()

            val response = knowledgeServiceStub.getKnowledgeDetail(request)

            Knowledge.fromProto(response.knowledgeDetail)
        }
    }

    suspend fun getPopularKnowledgeList(): PopularKnowledgeListGetResponse {
        return withContext(Dispatchers.IO) {
            val response = knowledgeServiceStub.getPopularKnowledgeList(Empty.getDefaultInstance())

            PopularKnowledgeListGetResponse.fromProto(response)
        }
    }
}