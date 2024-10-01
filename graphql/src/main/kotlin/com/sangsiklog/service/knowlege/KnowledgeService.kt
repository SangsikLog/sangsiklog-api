package com.sangsiklog.service.knowlege

import com.google.protobuf.Empty
import com.sangsiklog.config.GrpcProperties
import com.sangsiklog.core.grpc.GrpcClient
import com.sangsiklog.model.SortDirection
import com.sangsiklog.model.knowledge.Knowledge
import com.sangsiklog.model.knowledge.KnowledgeCountGetResponse
import com.sangsiklog.model.knowledge.KnowledgeListGetResponse
import com.sangsiklog.model.knowledge.PopularKnowledgeListGetResponse
import com.sangsiklog.service.knowledge.KnowledgeServiceGrpcKt
import com.sangsiklog.service.knowledge.KnowledgeServiceOuterClass.*
import common.Common.Pageable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class KnowledgeService(
    grpcClient: GrpcClient,
    grpcProperties: GrpcProperties,
    private val redisTemplate: RedisTemplate<String, Any>
) {
    private val knowledgeServiceStub = grpcClient.createStub(
        stubClass = KnowledgeServiceGrpcKt.KnowledgeServiceCoroutineStub::class,
        serviceName = grpcProperties.contentApi.serviceName,
        port = grpcProperties.contentApi.port
    )

    suspend fun registerKnowledge(userId: Long, title: String, description: String, categoryId: Long): Knowledge {
        return withContext(Dispatchers.IO) {
            val request = KnowledgeRegistrationRequest.newBuilder()
                .setUserId(userId)
                .setTitle(title)
                .setDescription(description)
                .setCategoryId(categoryId)
                .build()

            val response = knowledgeServiceStub.registerKnowledge(request)

            Knowledge.fromProto(response)
        }
    }

    suspend fun getKnowledgeList(page: Int, size: Int, sortColumn: String, direction: SortDirection, categoryId: Long?): KnowledgeListGetResponse {
        return withContext(Dispatchers.IO) {
            val pageable = Pageable.newBuilder()
                .setPage(page)
                .setSize(size)
                .setSortColumn(sortColumn)
                .setDirection(direction.toProto())
                .build()

            val requestBuilder = KnowledgeListGetRequest.newBuilder()
                .setPageable(pageable)
            categoryId?.let { requestBuilder.setCategoryId(it) }

            val request = requestBuilder.build()

            val response = knowledgeServiceStub.getKnowledgeList(request)

            KnowledgeListGetResponse.fromProto(response)
        }
    }

    suspend fun getKnowledgeDetail(knowledgeId: Long): Knowledge {
        return withContext(Dispatchers.IO) {
            val request = KnowledgeDetailGetRequest.newBuilder()
                .setKnowledgeId(knowledgeId)
                .build()

            val response = knowledgeServiceStub.getKnowledgeDetail(request)

            Knowledge.fromProto(response.knowledgeDetail)
        }
    }

    suspend fun getPopularKnowledgeList(categoryId: Long?): PopularKnowledgeListGetResponse {
        return withContext(Dispatchers.IO) {
            val requestBuilder = PopularKnowledgeListGetRequest.newBuilder()
            categoryId?.let {
                requestBuilder.setCategoryId(it)
            }

            val request = requestBuilder.build()

            val response = knowledgeServiceStub.getPopularKnowledgeList(request)

            PopularKnowledgeListGetResponse.fromProto(response)
        }
    }

    @Cacheable("daily_knowledge")
    suspend fun getDailyKnowledge(): Knowledge {
        return withContext(Dispatchers.IO) {
            val response = knowledgeServiceStub.getRandomKnowledge(Empty.getDefaultInstance())

            val result = Knowledge.fromProto(response.knowledgeDetail)

            redisTemplate.opsForValue().set("daily_knowledge", result, 24, TimeUnit.HOURS)

            result
        }
    }

    suspend fun getKnowledgeCount(): KnowledgeCountGetResponse {
        return withContext(Dispatchers.IO) {
            val response = knowledgeServiceStub.getKnowledgeCount(Empty.getDefaultInstance())

            KnowledgeCountGetResponse.fromProto(response)
        }
    }

    suspend fun searchKnowledge(query: String, categoryId: Long?, page: Int, size: Int): KnowledgeListGetResponse {
        return withContext(Dispatchers.IO) {
            val pageable = Pageable.newBuilder()
                .setPage(page)
                .setSize(size)
                .build()

            val requestBuilder = KnowledgeSearchRequest.newBuilder()
                .setQuery(query)
                .setPageable(pageable)
            categoryId?.let { requestBuilder.setCategoryId(it) }

            val request = requestBuilder.build()

            val response = knowledgeServiceStub.searchKnowledge(request)

            KnowledgeListGetResponse.fromProto(response)
        }
    }
}