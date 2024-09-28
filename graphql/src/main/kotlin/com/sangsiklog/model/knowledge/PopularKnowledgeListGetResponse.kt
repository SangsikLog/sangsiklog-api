package com.sangsiklog.model.knowledge

import com.sangsiklog.service.knowledge.KnowledgeServiceOuterClass

class PopularKnowledgeListGetResponse(
    val knowledgeList: List<Knowledge> = emptyList()
)  {
    companion object {
        fun fromProto(proto: KnowledgeServiceOuterClass.PopularKnowledgeListGetResponse, likeCounts: List<KnowledgeLikeCount>): PopularKnowledgeListGetResponse {
            val knowledgeList = proto.knowledgeDetailList
                .map { Knowledge.fromWithLikeCount(it,
                    likeCounts.firstOrNull { likeCount -> likeCount.knowledgeId == it.knowledgeId })
                }
                .toList()

            return PopularKnowledgeListGetResponse(
                knowledgeList = knowledgeList
            )
        }
    }
}