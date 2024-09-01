package com.sangsiklog.controller

import com.sangsiklog.model.SortDirection
import com.sangsiklog.model.knowledge.Knowledge
import com.sangsiklog.model.knowledge.KnowledgeListGetResponse
import com.sangsiklog.service.knowlege.KnowledgeService
import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
@RequiredArgsConstructor
class KnowledgeController(
    private val knowledgeService: KnowledgeService
) {
    @MutationMapping
    suspend fun registerKnowledge(
        @Argument userId: Long,
        @Argument title: String,
        @Argument description: String
    ): Knowledge {
        return knowledgeService.registerKnowledge(userId, title, description)
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
}