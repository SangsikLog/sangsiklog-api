package com.sangsiklog.service.knowledge

import com.google.protobuf.Empty
import com.sangsiklog.core.api.exception.ErrorType
import com.sangsiklog.domain.knowledge.Knowledge
import com.sangsiklog.exception.knowledge.KnowledgeServiceException
import com.sangsiklog.repository.category.CategoryRepository
import com.sangsiklog.repository.knowledge.KnowledgeRepository
import com.sangsiklog.service.knowledge.KnowledgeServiceOuterClass.*
import com.sangsiklog.service.model.knowledge.KnowledgeDetail
import com.sangsiklog.utils.PageableUtil
import common.Common.PagerInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class KnowledgeService(
    private val repository: KnowledgeRepository,
    private val categoryRepository: CategoryRepository
): KnowledgeServiceGrpcKt.KnowledgeServiceCoroutineImplBase() {
    @Transactional
    override suspend fun registerKnowledge(request: KnowledgeRegistrationRequest): KnowledgeRegistrationResponse {
        return withContext(Dispatchers.IO) {
            val category = categoryRepository.findById(request.categoryId)
                .orElseThrow { KnowledgeServiceException(ErrorType.NOT_FOUND_CATEGORY) }

            val knowledge = Knowledge.create(
                userId = request.userId,
                title = request.title,
                description = request.description,
                category = category
            )

            repository.save(knowledge)

            KnowledgeRegistrationResponse.newBuilder()
                .setKnowledgeId(knowledge.id!!)
                .build()
        }
    }

    override suspend fun getKnowledgeList(request: KnowledgeListGetRequest): KnowledgeListGetResponse {
        return withContext(Dispatchers.IO) {
            val pageable = PageableUtil.createByProto(request.pageable)

            val knowledgeList = repository.findAll(pageable)

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

    override suspend fun getPopularKnowledgeList(request: Empty): PopularKnowledgeListGetResponse {
        return withContext(Dispatchers.IO) {
            val knowledgeList = repository.findPopularKnowledgeByLikes()
            val knowledgeDetailList = knowledgeList
                .map { KnowledgeDetail.from(it).toProto() }
                .toList()

            PopularKnowledgeListGetResponse.newBuilder()
                .addAllKnowledgeDetail(knowledgeDetailList)
                .build()
        }
    }
}