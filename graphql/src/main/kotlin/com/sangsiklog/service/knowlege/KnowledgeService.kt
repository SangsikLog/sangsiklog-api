package com.sangsiklog.service.knowlege

import com.sangsiklog.model.SortDirection
import com.sangsiklog.model.knowledge.Knowledge
import com.sangsiklog.model.knowledge.KnowledgeListGetResponse
import com.sangsiklog.service.knowledge.KnowledgeServiceOuterClass.KnowledgeDetailGetRequest
import com.sangsiklog.service.knowledge.KnowledgeServiceOuterClass.KnowledgeListGetRequest
import com.sangsiklog.utils.grpc.GrpcClient
import com.sangsiklog.service.knowledge.KnowledgeServiceOuterClass.KnowledgeRegistrationRequest
import common.Common.Pageable
import org.springframework.stereotype.Service

@Service
class KnowledgeService(
    grpcClient: GrpcClient
) {
    private val knowledgeServiceStub = grpcClient.createKnowledgeServiceStub()

    suspend fun registerKnowledge(userId: Long, title: String, description: String): Knowledge {
        val request = KnowledgeRegistrationRequest.newBuilder()
            .setUserId(userId)
            .setTitle(title)
            .setDescription(description)
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