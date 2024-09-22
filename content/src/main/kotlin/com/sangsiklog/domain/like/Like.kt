package com.sangsiklog.domain.like

import com.sangsiklog.domain.base.BaseEntity
import com.sangsiklog.domain.knowledge.Knowledge
import jakarta.persistence.*

@Entity
@SequenceGenerator(
    name = "LIKE_SEQ_GENERATOR",
    sequenceName = "LIKE_SEQ",
    initialValue = 1,
    allocationSize = 50
)
@Table(name = "likes", uniqueConstraints = [UniqueConstraint(columnNames = arrayOf("knowledge_id", "user_id"))])
class Like(
    knowledge: Knowledge,
    userId: Long
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIKE_SEQ_GENERATOR")
    @Column(name = "like_id")
    val id: Long? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "knowledge_id", nullable = false)
    val knowledge: Knowledge = knowledge

    @Column(name = "user_id", nullable = false)
    val userId: Long = userId

    companion object {
        fun create(knowledge: Knowledge, userId: Long,): Like {
            return Like(
                knowledge = knowledge,
                userId = userId
            )
        }
    }
}