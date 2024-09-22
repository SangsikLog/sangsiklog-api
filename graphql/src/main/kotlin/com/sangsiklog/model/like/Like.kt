package com.sangsiklog.model.like

import com.sangsiklog.service.like.LikeServiceOuterClass.KnowledgeLikeAddResponse
import com.sangsiklog.service.like.LikeServiceOuterClass.KnowledgeLikeRemoveResponse

data class Like(
    val id: Long = 0,
    val knowledgeId: Long = 0,
    val userId: Long = 0,
) {
    companion object {
        fun fromProto(proto: KnowledgeLikeAddResponse): Like {
            return Like(
                id = proto.likeId
            )
        }

        fun fromProto(proto: KnowledgeLikeRemoveResponse): Like {
            return Like(
                id = proto.likeId
            )
        }
    }
}
