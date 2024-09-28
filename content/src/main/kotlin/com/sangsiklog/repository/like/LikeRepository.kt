package com.sangsiklog.repository.like

import com.sangsiklog.domain.like.Like
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LikeRepository: JpaRepository<Like, Long> {

    fun findByKnowledgeIdAndUserId(knowledgeId: Long, userId: Long): Optional<Like>

    fun findByKnowledgeIdIn(knowledgeIds: List<Long>): List<Like>
}