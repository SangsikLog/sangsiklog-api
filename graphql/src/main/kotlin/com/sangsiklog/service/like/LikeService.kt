package com.sangsiklog.service.like

import com.sangsiklog.config.GrpcProperties
import com.sangsiklog.core.grpc.GrpcClient
import com.sangsiklog.model.knowledge.KnowledgeLikeCount
import com.sangsiklog.model.like.Like
import com.sangsiklog.service.like.LikeServiceOuterClass.KnowledgeLikeAddRequest
import com.sangsiklog.service.like.LikeServiceOuterClass.KnowledgeLikeCountsRequest
import com.sangsiklog.service.like.LikeServiceOuterClass.KnowledgeLikeRemoveRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class LikeService(
    grpcClient: GrpcClient,
    grpcProperties: GrpcProperties
) {
    private val likeServiceStub = grpcClient.createStub(
        stubClass = LikeServiceGrpcKt.LikeServiceCoroutineStub::class,
        serviceName = grpcProperties.contentApi.serviceName,
        port = grpcProperties.contentApi.port
    )

    suspend fun addKnowledgeLike(knowledgeId: Long, userId: Long): Like {
        return withContext(Dispatchers.IO) {
            val request = KnowledgeLikeAddRequest.newBuilder()
                .setKnowledgeId(knowledgeId)
                .setUserId(userId)
                .build()

            val response = likeServiceStub.addKnowledgeLike(request)

            Like.fromProto(response)
        }
    }

    suspend fun removeKnowledgeLike(knowledgeId: Long, userId: Long): Like {
        return withContext(Dispatchers.IO) {
            val request = KnowledgeLikeRemoveRequest.newBuilder()
                .setKnowledgeId(knowledgeId)
                .setUserId(userId)
                .build()

            val response = likeServiceStub.removeKnowledgeLike(request)

            Like.fromProto(response)
        }
    }

    suspend fun getLikeCounts(knowledgeIds: Set<Long>): List<KnowledgeLikeCount> {
        val request = KnowledgeLikeCountsRequest.newBuilder()
            .addAllKnowledgeIds(knowledgeIds)
            .build()

        val response = likeServiceStub.getKnowledgeLikeCounts(request)

        return response.knowledgeLikeCountList
            .map { KnowledgeLikeCount.fromProto(it) }

    }
}