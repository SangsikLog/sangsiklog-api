package com.sangsiklog.domain.listener

import com.sangsiklog.domain.knowledge.Knowledge
import com.sangsiklog.service.model.knowledge.KnowledgeDocument
import jakarta.persistence.PostPersist
import jakarta.persistence.PostRemove
import jakarta.persistence.PostUpdate
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate

class KnowledgeListener(
    private val elasticsearchTemplate: ElasticsearchRestTemplate
) {
    @PostPersist
    fun onPostPersist(knowledge: Knowledge) {
        val productDocument = KnowledgeDocument(
            id = knowledge.id!!,
            title = knowledge.title,
            description = knowledge.description,
            categoryId = knowledge.category.id,
            createdAt = knowledge.createdAt
        )

        elasticsearchTemplate.save(productDocument)
    }

    @PostUpdate
    fun onPostUpdate(knowledge: Knowledge) {
        val productDocument = KnowledgeDocument(
            id = knowledge.id!!,
            title = knowledge.title,
            description = knowledge.description,
            categoryId = knowledge.category.id,
            createdAt = knowledge.createdAt
        )

        elasticsearchTemplate.save(productDocument)
    }

    @PostRemove
    fun onPostRemove(knowledge: Knowledge) {
        elasticsearchTemplate.delete(knowledge.id.toString(), KnowledgeDocument::class.java)
    }
}