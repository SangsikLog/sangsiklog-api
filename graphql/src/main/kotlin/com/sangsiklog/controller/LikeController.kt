package com.sangsiklog.controller

import com.sangsiklog.model.like.Like
import com.sangsiklog.service.like.LikeService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class LikeController(
    private val lIkeService: LikeService
) {
    @MutationMapping
    suspend fun addKnowledgeLike(
        @Argument knowledgeId: Long,
        @Argument userId: Long
    ): Like {
        return lIkeService.addKnowledgeLike(knowledgeId, userId)
    }

    @MutationMapping
    suspend fun removeKnowledgeLike(
        @Argument knowledgeId: Long,
        @Argument userId: Long
    ): Like {
        return lIkeService.removeKnowledgeLike(knowledgeId, userId)
    }
}