package com.sangsiklog.repository.knowledge

import com.sangsiklog.domain.knowledge.Knowledge
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface KnowledgeRepository: JpaRepository<Knowledge, Long>, KnowledgeRepositoryCustom {
}