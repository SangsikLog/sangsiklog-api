package com.sangsiklog.model.knowledge

import com.sangsiklog.model.PagerInfo
import com.sangsiklog.service.knowledge.KnowledgeServiceOuterClass

class KnowledgeListGetResponse(
    val knowledgeList: List<Knowledge> = emptyList(),
    val pagerInfo: PagerInfo? = null,
)  {
    companion object {
        fun fromProto(proto: KnowledgeServiceOuterClass.KnowledgeListGetResponse, likeCounts: List<KnowledgeLikeCount>): KnowledgeListGetResponse {
            val knowledgeList = proto.knowledgeDetailList
                .map { Knowledge.fromWithLikeCount(it,
                    likeCounts.first { likeCount -> likeCount.knowledgeId == it.knowledgeId }) }
                .toList()

            return KnowledgeListGetResponse(
                knowledgeList = knowledgeList,
                pagerInfo = PagerInfo.fromProto(proto.pagerInfo)
            )
        }
    }
}