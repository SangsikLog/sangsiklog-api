package com.sangsiklog.service.like

import com.sangsiklog.core.api.exception.ErrorType
import com.sangsiklog.domain.like.Like
import com.sangsiklog.exception.like.LikeServiceException
import com.sangsiklog.repository.like.LikeRepository
import com.sangsiklog.service.like.LikeServiceOuterClass.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LikeService(
    private val likeRepository: LikeRepository
): LikeServiceGrpcKt.LikeServiceCoroutineImplBase() {

    @Transactional
    override suspend fun addKnowledgeLike(request: KnowledgeLikeAddRequest): KnowledgeLikeAddResponse {
        likeRepository.findByKnowledgeIdAndUserId(request.knowledgeId, request.userId)
            .ifPresent { throw LikeServiceException(ErrorType.ALREADY_LIKES) }

        val like = Like.create(request.knowledgeId, request.userId)

        likeRepository.save(like)

        return KnowledgeLikeAddResponse.newBuilder()
            .setLikeId(like.id!!)
            .build()
    }

    @Transactional
    override suspend fun removeKnowledgeLike(request: KnowledgeLikeRemoveRequest): KnowledgeLikeRemoveResponse {
        val like = likeRepository.findByKnowledgeIdAndUserId(request.knowledgeId, request.userId)
            .orElseThrow { LikeServiceException(ErrorType.NOT_FOUND_LIKE) }

        likeRepository.delete(like)

        return KnowledgeLikeRemoveResponse.newBuilder()
            .setLikeId(like.id!!)
            .build()
    }

    override suspend fun getKnowledgeLikeCounts(request: KnowledgeLikeCountsRequest): KnowledgeLikeCountsResponse {
        return withContext(Dispatchers.IO) {
            val likes = likeRepository.findByKnowledgeIdIn(request.knowledgeIdsList)

            val knowledgeLikeCounts = likes.groupBy { it.knowledgeId }
                .map { entry -> KnowledgeLikeCount.newBuilder()
                    .setKnowledgeId(entry.key)
                    .setCount(entry.value.size.toLong())
                    .build()
                }

            KnowledgeLikeCountsResponse.newBuilder()
                .addAllKnowledgeLikeCount(knowledgeLikeCounts)
                .build()
        }
    }
}