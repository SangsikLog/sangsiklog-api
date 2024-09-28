package com.sangsiklog.model.knowledge

import com.sangsiklog.service.like.LikeServiceOuterClass

data class KnowledgeLikeCount(
    val knowledgeId: Long = 0,
    val count: Long = 0
) {
    companion object {
        fun fromProto(proto: LikeServiceOuterClass.KnowledgeLikeCount): KnowledgeLikeCount {
            return KnowledgeLikeCount(
                knowledgeId = proto.knowledgeId,
                count = proto.count
            )
        }
    }
}
