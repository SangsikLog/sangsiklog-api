package com.sangsiklog.service.knowlege

import com.sangsiklog.model.knowledge.Knowledge
import com.sangsiklog.utils.grpc.GrpcClient
import com.sangsiklog.service.knowledge.KnowledgeServiceOuterClass.KnowledgeRegistrationRequest
import org.springframework.stereotype.Service

@Service
class KnowledgeService(
    grpcClient: GrpcClient
) {
    private val knowledgeServiceStub = grpcClient.createKnowledgeServiceStub()

    suspend fun registerKnowledge(userId: Long, title: String, description: String): Knowledge? {
        val request = KnowledgeRegistrationRequest.newBuilder()
            .setUserId(userId)
            .setTitle(title)
            .setDescription(description)
            .build()

        val response = knowledgeServiceStub.registerKnowledge(request)

        return Knowledge.fromProto(response)
    }
}