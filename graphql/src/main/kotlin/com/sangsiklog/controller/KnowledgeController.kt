package com.sangsiklog.controller

import com.sangsiklog.model.SortDirection
import com.sangsiklog.model.knowledge.Knowledge
import com.sangsiklog.model.knowledge.KnowledgeCountGetResponse
import com.sangsiklog.model.knowledge.KnowledgeListGetResponse
import com.sangsiklog.model.knowledge.PopularKnowledgeListGetResponse
import com.sangsiklog.service.knowlege.KnowledgeService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class KnowledgeController(
    private val knowledgeService: KnowledgeService
) {
    @MutationMapping
    suspend fun registerKnowledge(
        @Argument userId: Long,
        @Argument title: String,
        @Argument description: String,
        @Argument categoryId: Long
    ): Knowledge {
        return knowledgeService.registerKnowledge(userId, title, description, categoryId)
    }

    @QueryMapping
    suspend fun getKnowledgeList(
        @Argument page: Int,
        @Argument size: Int,
        @Argument sortColumn: String,
        @Argument direction: SortDirection = SortDirection.DESC
    ): KnowledgeListGetResponse {
        return knowledgeService.getKnowledgeList(page, size, sortColumn, direction)
    }

    @QueryMapping
    suspend fun getKnowledgeDetail(
        @Argument knowledgeId: Long
    ): Knowledge {
        return knowledgeService.getKnowledgeDetail(knowledgeId)
    }

    @QueryMapping
    suspend fun getPopularKnowledgeList(): PopularKnowledgeListGetResponse {
        return knowledgeService.getPopularKnowledgeList()
    }

    @QueryMapping
    suspend fun getDailyKnowledge(): Knowledge {
        return knowledgeService.getDailyKnowledge()
    }

    @QueryMapping
    suspend fun getKnowledgeCount(): KnowledgeCountGetResponse {
        return knowledgeService.getKnowledgeCount()
    }
}