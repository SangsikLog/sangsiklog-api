package com.sangsiklog.dataloader

import com.sangsiklog.model.knowledge.KnowledgeLikeCount
import com.sangsiklog.service.like.LikeService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.future
import org.dataloader.BatchLoaderEnvironment
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class KnowledgeLikeCountDataLoader(
    private val likeService: LikeService
) {
    fun createDataLoader(): (Set<Long>, BatchLoaderEnvironment) -> Mono<Map<Long, KnowledgeLikeCount>>  {
        return { knowledgeIds, env ->
            Mono.fromFuture(
                CoroutineScope(Dispatchers.IO).future {
                    likeService.getLikeCounts(knowledgeIds).associateBy { it.knowledgeId }
                }
            )
        }
    }
}