package com.sangsiklog.model.knowledge

import com.sangsiklog.service.knowledge.KnowledgeServiceOuterClass.KnowledgeDetail
import com.sangsiklog.service.knowledge.KnowledgeServiceOuterClass.KnowledgeRegistrationResponse
import com.sangsiklog.utils.DateTimeUtils

data class Knowledge(
    val id: Long = 0,
    val userId: Long = 0,
    val title: String = "",
    val description: String = "",
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
                createdAt = DateTimeUtils.milliToLocalDateTime(proto.createdAt).toString(),
            )
        }
    }
}
