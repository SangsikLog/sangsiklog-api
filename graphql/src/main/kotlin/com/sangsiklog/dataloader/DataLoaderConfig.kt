package com.sangsiklog.dataloader

import com.sangsiklog.model.knowledge.KnowledgeLikeCount
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.BatchLoaderRegistry

@Configuration
class DataLoaderConfig(
    private val knowledgeLikeCountDataLoader: KnowledgeLikeCountDataLoader
) {
    @Bean
    fun registerDataLoader(batchLoaderRegistry: BatchLoaderRegistry): Any {
        batchLoaderRegistry.forTypePair(Long::class.java, KnowledgeLikeCount::class.java)
            .withName("knowledgeLikeCount")
            .registerMappedBatchLoader(knowledgeLikeCountDataLoader.createDataLoader())

        return Any()
    }
}