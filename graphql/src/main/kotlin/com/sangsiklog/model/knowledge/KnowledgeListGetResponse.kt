package com.sangsiklog.model.knowledge

import com.sangsiklog.model.PagerInfo
import com.sangsiklog.service.knowledge.KnowledgeServiceOuterClass

class KnowledgeListGetResponse(
    val knowledgeList: List<Knowledge> = emptyList(),
    val pagerInfo: PagerInfo? = null,
)  {
    companion object {
        fun fromProto(proto: KnowledgeServiceOuterClass.KnowledgeListGetResponse): KnowledgeListGetResponse {
            val knowledgeList = proto.knowledgeDetailList
                .map { Knowledge.fromProto(it) }
                .toList()

            return KnowledgeListGetResponse(
                knowledgeList = knowledgeList,
                pagerInfo = PagerInfo.fromProto(proto.pagerInfo)
            )
        }
    }
}