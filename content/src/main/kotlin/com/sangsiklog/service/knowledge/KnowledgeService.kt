package com.sangsiklog.service.knowledge

import com.sangsiklog.core.utils.DateTimeUtils.toUnixTimestampInMilliseconds
import com.sangsiklog.domain.knowledge.Knowledge
import com.sangsiklog.repository.knowledge.KnowledgeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class KnowledgeService(
    private val repository: KnowledgeRepository
): KnowledgeServiceGrpcKt.KnowledgeServiceCoroutineImplBase() {
    override suspend fun registerKnowledge(request: KnowledgeServiceOuterClass.KnowledgeRegistrationRequest): KnowledgeServiceOuterClass.KnowledgeRegistrationResponse {
        return withContext(Dispatchers.IO) {
            val knowledge = Knowledge.create(
                userId = request.userId,
                title = request.title,
                description = request.description
            )

            repository.save(knowledge)

            KnowledgeServiceOuterClass.KnowledgeRegistrationResponse.newBuilder()
                .setKnowledgeId(knowledge.id!!)
                .setTitle(knowledge.title)
                .setDescription(knowledge.description)
                .setCreatedAt(knowledge.createdAt!!.toUnixTimestampInMilliseconds())
                .build()
        }
    }
}