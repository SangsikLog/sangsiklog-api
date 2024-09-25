package com.sangsiklog.model.knowledge

import com.sangsiklog.service.knowledge.KnowledgeServiceOuterClass

data class KnowledgeCountGetResponse(
    val count: Long = 0,
) {
    companion object {
        fun fromProto(proto: KnowledgeServiceOuterClass.KnowledgeCountGetResponse): KnowledgeCountGetResponse {
            return KnowledgeCountGetResponse(
                count = proto.count
            )
        }
    }
}
