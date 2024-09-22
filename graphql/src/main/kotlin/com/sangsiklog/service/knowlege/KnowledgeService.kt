package com.sangsiklog.service.knowlege

import com.sangsiklog.config.GrpcProperties
import com.sangsiklog.core.grpc.GrpcClient
import com.sangsiklog.model.SortDirection
import com.sangsiklog.model.knowledge.Knowledge
import com.sangsiklog.model.knowledge.KnowledgeListGetResponse
import com.sangsiklog.service.knowledge.KnowledgeServiceGrpcKt
import com.sangsiklog.service.knowledge.KnowledgeServiceOuterClass.*
import common.Common.Pageable
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
        val request = KnowledgeRegistrationRequest.newBuilder()
            .setUserId(userId)
            .setTitle(title)
            .setDescription(description)
            .setCategoryId(categoryId)
            .build()

        val response = knowledgeServiceStub.registerKnowledge(request)

        return Knowledge.fromProto(response)
    }

    suspend fun getKnowledgeList(page: Int, size: Int, sortColumn: String, direction: SortDirection): KnowledgeListGetResponse {
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

        return KnowledgeListGetResponse.fromProto(response)
    }

    suspend fun getKnowledgeDetail(knowledgeId: Long): Knowledge {
        val request = KnowledgeDetailGetRequest.newBuilder()
            .setKnowledgeId(knowledgeId)
            .build()

        val response = knowledgeServiceStub.getKnowledgeDetail(request)

        return Knowledge.fromProto(response.knowledgeDetail)
    }
}