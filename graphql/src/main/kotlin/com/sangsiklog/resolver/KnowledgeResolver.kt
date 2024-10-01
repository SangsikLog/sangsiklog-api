package com.sangsiklog.resolver

import com.sangsiklog.model.knowledge.Knowledge
import com.sangsiklog.model.knowledge.KnowledgeLikeCount
import org.dataloader.DataLoader
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import java.util.concurrent.CompletableFuture

@Controller
class KnowledgeResolver {
    @SchemaMapping(typeName = "Knowledge", field = "likeCount")
    fun getLikeCount(knowledge: Knowledge, knowledgeLikeCount: DataLoader<Long, KnowledgeLikeCount>): CompletableFuture<Long> {

        return knowledgeLikeCount.load(knowledge.id).thenApply { likeCount ->
            likeCount?.count ?: 0
        }
    }
}