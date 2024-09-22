package com.sangsiklog.domain.like

import com.sangsiklog.domain.base.BaseEntity
import jakarta.persistence.*

@Entity
@SequenceGenerator(
    name = "LIKE_SEQ_GENERATOR",
    sequenceName = "LIKE_SEQ",
    initialValue = 1,
    allocationSize = 50
)
@Table(name = "likes", uniqueConstraints = [UniqueConstraint(columnNames = arrayOf("knowledge_id", "user_id"))],
    indexes = [Index(name = "idx_knowledge_user", columnList = "knowledge_id, user_id")])
class Like(
    knowledgeId: Long,
    userId: Long
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIKE_SEQ_GENERATOR")
    @Column(name = "like_id")
    val id: Long? = null

    @Column(name = "knowledge_id", nullable = false)
    val knowledgeId: Long = knowledgeId

    @Column(name = "user_id", nullable = false)
    val userId: Long = userId

    companion object {
        fun create(knowledgeId: Long, userId: Long): Like {
            return Like(
                knowledgeId = knowledgeId,
                userId = userId
            )
        }
    }
}