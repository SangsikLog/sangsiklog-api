package com.sangsiklog.controller

import com.sangsiklog.model.knowledge.Knowledge
import com.sangsiklog.service.knowlege.KnowledgeService
import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
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
    ): Knowledge? {
        return knowledgeService.registerKnowledge(userId, title, description)
    }
}