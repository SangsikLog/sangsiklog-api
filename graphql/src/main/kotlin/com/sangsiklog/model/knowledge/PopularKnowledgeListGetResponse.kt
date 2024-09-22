package com.sangsiklog.model.knowledge

import com.sangsiklog.service.knowledge.KnowledgeServiceOuterClass

class PopularKnowledgeListGetResponse(
    val knowledgeList: List<Knowledge> = emptyList()
)  {
    companion object {
        fun fromProto(proto: KnowledgeServiceOuterClass.PopularKnowledgeListGetResponse): PopularKnowledgeListGetResponse {
            val knowledgeList = proto.knowledgeDetailList
                .map { Knowledge.fromProto(it) }
                .toList()

            return PopularKnowledgeListGetResponse(
                knowledgeList = knowledgeList
            )
        }
    }
}