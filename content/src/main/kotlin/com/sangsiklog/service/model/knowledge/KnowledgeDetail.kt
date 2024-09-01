package com.sangsiklog.service.model.knowledge

import com.sangsiklog.core.utils.DateTimeUtils.toUnixTimestampInMilliseconds
import com.sangsiklog.domain.knowledge.Knowledge
import com.sangsiklog.service.knowledge.KnowledgeServiceOuterClass
import java.time.LocalDateTime

class KnowledgeDetail(
    val id: Long = 0,
    val title: String? = null,
    val description: String? = null,
    val createdAt: LocalDateTime? = null
) {
    companion object {
        fun from(knowledge: Knowledge): KnowledgeDetail {
            return KnowledgeDetail(
                id = knowledge.id!!,
                title = knowledge.title,
                description = knowledge.description,
                createdAt = knowledge.createdAt
            )
        }
    }

    fun toProto(): KnowledgeServiceOuterClass.KnowledgeDetail {
        return KnowledgeServiceOuterClass.KnowledgeDetail.newBuilder()
            .setKnowledgeId(this.id)
            .setTitle(this.title)
            .setDescription(this.description)
            .setCreatedAt(this.createdAt!!.toUnixTimestampInMilliseconds())
            .build()
    }
}