package com.sangsiklog.model.knowledge

import com.sangsiklog.core.utils.DateTimeUtils
import com.sangsiklog.model.category.Category
import com.sangsiklog.service.knowledge.KnowledgeServiceOuterClass.KnowledgeDetail
import com.sangsiklog.service.knowledge.KnowledgeServiceOuterClass.KnowledgeRegistrationResponse

data class Knowledge(
    val id: Long = 0,
    val userId: Long = 0,
    val title: String = "",
    val description: String = "",
    val category: Category? = null,
    val likeCount: Long = 0,
    val createdAt: String? = null
) {
    companion object {
        fun fromProto(proto: KnowledgeRegistrationResponse): Knowledge {
            return Knowledge(
                id = proto.knowledgeId
            )
        }

        fun fromProto(proto: KnowledgeDetail): Knowledge {
            return Knowledge(
                id = proto.knowledgeId,
                title = proto.title,
                description = proto.description,
                category = proto.category?.let { Category.fromProto(it) },
                createdAt = DateTimeUtils.milliToLocalDateTime(proto.createdAt).toString(),
            )
        }
    }
}
