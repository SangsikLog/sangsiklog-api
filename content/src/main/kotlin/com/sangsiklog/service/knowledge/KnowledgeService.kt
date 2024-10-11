package com.sangsiklog.service.knowledge

import com.google.protobuf.Empty
import com.sangsiklog.core.api.exception.ErrorType
import com.sangsiklog.domain.category.Category
import com.sangsiklog.domain.knowledge.Knowledge
import com.sangsiklog.exception.knowledge.KnowledgeServiceException
import com.sangsiklog.repository.category.CategoryRepository
import com.sangsiklog.repository.knowledge.KnowledgeRepository
import com.sangsiklog.service.knowledge.KnowledgeServiceOuterClass.*
import com.sangsiklog.service.model.knowledge.KnowledgeDetail
import com.sangsiklog.service.model.knowledge.KnowledgeDocument
import com.sangsiklog.utils.PageableUtil
import common.Common.PagerInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.elasticsearch.index.query.QueryBuilders
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class KnowledgeService(
    private val repository: KnowledgeRepository,
    private val categoryRepository: CategoryRepository,
    private val elasticsearchTemplate: ElasticsearchRestTemplate
): KnowledgeServiceGrpcKt.KnowledgeServiceCoroutineImplBase() {
    @Transactional
    override suspend fun registerKnowledge(request: KnowledgeRegistrationRequest): KnowledgeRegistrationResponse {
        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow { KnowledgeServiceException(ErrorType.NOT_FOUND_CATEGORY) }

        val knowledge = Knowledge.create(
            userId = request.userId,
            title = request.title,
            description = request.description,
            category = category
        )

        repository.save(knowledge)

        return KnowledgeRegistrationResponse.newBuilder()
            .setKnowledgeId(knowledge.id!!)
            .build()
    }

    override suspend fun getKnowledgeList(request: KnowledgeListGetRequest): KnowledgeListGetResponse {
        return withContext(Dispatchers.IO) {
            val category: Category? = if (request.hasCategoryId()) {
                categoryRepository.findById(request.categoryId)
                    .orElseThrow{ KnowledgeServiceException(ErrorType.NOT_FOUND_CATEGORY) }
            } else {
                null
            }

            val pageable = PageableUtil.createByProto(request.pageable)

            val knowledgeList = repository.findAllByCategory(category, pageable)

            val knowledgeDetailList = knowledgeList
                .map { KnowledgeDetail.from(it).toProto() }
                .toList()

            val pagerInfo = PagerInfo.newBuilder()
                .setTotalCount(knowledgeList.totalElements)
                .build()

            KnowledgeListGetResponse.newBuilder()
                .addAllKnowledgeDetail(knowledgeDetailList)
                .setPagerInfo(pagerInfo)
                .build()
        }
    }

    override suspend fun getKnowledgeDetail(request: KnowledgeDetailGetRequest): KnowledgeDetailGetResponse {
       return withContext(Dispatchers.IO) {
           val knowledge = repository.findById(request.knowledgeId)
               .orElseThrow{ KnowledgeServiceException(ErrorType.NOT_FOUND_KNOWLEDGE) }

           KnowledgeDetailGetResponse.newBuilder()
               .setKnowledgeDetail(KnowledgeDetail.from(knowledge).toProto())
               .build()
       }
    }

    override suspend fun getPopularKnowledgeList(request: PopularKnowledgeListGetRequest): PopularKnowledgeListGetResponse {
        return withContext(Dispatchers.IO) {
            val category: Category? = if (request.hasCategoryId()) {
                categoryRepository.findById(request.categoryId)
                    .orElseThrow{ KnowledgeServiceException(ErrorType.NOT_FOUND_CATEGORY) }
            } else {
                null
            }

            val knowledgeList = repository.findPopularKnowledgeByLikes(category)
            val knowledgeDetailList = knowledgeList
                .map { KnowledgeDetail.from(it).toProto() }
                .toList()

            PopularKnowledgeListGetResponse.newBuilder()
                .addAllKnowledgeDetail(knowledgeDetailList)
                .build()
        }
    }

    override suspend fun getRandomKnowledge(request: Empty): RandomKnowledgeGetResponse {
        return withContext(Dispatchers.IO) {
            val knowledge = repository.findDailyKnowledge()
                .orElseThrow { KnowledgeServiceException(ErrorType.NOT_FOUND_KNOWLEDGE) }

            RandomKnowledgeGetResponse.newBuilder()
                .setKnowledgeDetail(KnowledgeDetail.from(knowledge).toProto())
                .build()
        }
    }

    override suspend fun getKnowledgeCount(request: Empty): KnowledgeCountGetResponse {
        return withContext(Dispatchers.IO) {
            KnowledgeCountGetResponse.newBuilder()
                .setCount(repository.count())
                .build()
        }
    }

    override suspend fun searchKnowledge(request: KnowledgeSearchRequest): KnowledgeListGetResponse {
        return withContext(Dispatchers.IO) {
            val boolQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.wildcardQuery("title", "*${request.query}*"))
                .apply {
                    if (request.hasCategoryId()) {
                        filter(QueryBuilders.termQuery("categoryId", request.categoryId))
                    }
                }

            val pageable = PageableUtil.createByProto(request.pageable)

            val query = NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withPageable(pageable)
                .build()

            val searchHits = elasticsearchTemplate.search(query, KnowledgeDocument::class.java)
            val knowledgeDetailList = searchHits.searchHits.map { KnowledgeDetail.from(it.content).toProto() }

            val pagerInfo = PagerInfo.newBuilder()
                .setTotalCount(searchHits.totalHits)
                .build()

            KnowledgeListGetResponse.newBuilder()
                .addAllKnowledgeDetail(knowledgeDetailList)
                .setPagerInfo(pagerInfo)
                .build()
        }
    }
}