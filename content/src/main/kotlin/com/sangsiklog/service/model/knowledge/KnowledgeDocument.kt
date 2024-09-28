package com.sangsiklog.service.model.knowledge

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import java.time.LocalDateTime

@Document(indexName = "knowledge")
data class KnowledgeDocument(
    @Id val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val categoryId: Long? = null,
    val createdAt: LocalDateTime? = null
)