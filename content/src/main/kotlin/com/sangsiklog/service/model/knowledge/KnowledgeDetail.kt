package com.sangsiklog.service.model.knowledge

import com.sangsiklog.core.utils.DateTimeUtils.toUnixTimestampInMilliseconds
import com.sangsiklog.domain.knowledge.Knowledge
import com.sangsiklog.service.knowledge.KnowledgeServiceOuterClass
import com.sangsiklog.service.model.category.CategoryDetail
import java.time.LocalDateTime

class KnowledgeDetail(
    val id: Long = 0,
    val title: String? = null,
    val description: String? = null,
    val categoryDetail: CategoryDetail? = null,
    val createdAt: LocalDateTime? = null
) {
    companion object {
        fun from(knowledge: Knowledge): KnowledgeDetail {
            return KnowledgeDetail(
                id = knowledge.id!!,
                title = knowledge.title,
                description = knowledge.description,
                categoryDetail = CategoryDetail.from(knowledge.category),
                createdAt = knowledge.createdAt
            )
        }
    }

    fun toProto(): KnowledgeServiceOuterClass.KnowledgeDetail {
        return KnowledgeServiceOuterClass.KnowledgeDetail.newBuilder()
            .setKnowledgeId(this.id)
            .setTitle(this.title)
            .setDescription(this.description)
            .setCategory(this.categoryDetail?.toProto())
            .setCreatedAt(this.createdAt!!.toUnixTimestampInMilliseconds())
            .build()
    }
}