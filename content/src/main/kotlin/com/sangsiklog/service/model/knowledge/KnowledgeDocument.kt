package com.sangsiklog.service.model.knowledge

import com.sangsiklog.domain.knowledge.Knowledge
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Document(indexName = "knowledge")
data class KnowledgeDocument(
    @Id val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val categoryId: Long? = null,
    val createdAt: String = ""
) {
    companion object {
        fun fromKnowledge(knowledge: Knowledge): KnowledgeDocument {
            return KnowledgeDocument(
                id = knowledge.id!!,
                title = knowledge.title,
                description = knowledge.description,
                categoryId = knowledge.category.id,
                createdAt = knowledge.createdAt!!.format(DateTimeFormatter.ISO_DATE_TIME)
            )
        }
    }

    fun toLocalDateTime(): LocalDateTime {
        return LocalDateTime.parse(this.createdAt, DateTimeFormatter.ISO_DATE_TIME)
    }
}