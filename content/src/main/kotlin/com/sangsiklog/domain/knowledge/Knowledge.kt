package com.sangsiklog.domain.knowledge

import com.sangsiklog.domain.base.BaseEntity
import jakarta.persistence.*

@Entity
@SequenceGenerator(
    name = "KNOWLEDGE_SEQ_GENERATOR",
    sequenceName = "KNOWLEDGE_SEQ",
    initialValue = 1,
    allocationSize = 50
)
@Table(name = "knowledge")
class Knowledge(
    userId: Long,
    title: String,
    description: String
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KNOWLEDGE_SEQ_GENERATOR")
    @Column(name = "knowledge_id")
    val id: Long? = null

    @Column(name = "user_id", nullable = false)
    var userId: Long = userId

    @Column(name = "title", nullable = false)
    var title: String = title
        protected set

    @Column(name = "description", nullable = false)
    var description: String = description
        protected set

    companion object {
        fun create(userId: Long, title: String, description: String): Knowledge {
            return Knowledge(
                userId = userId,
                title = title,
                description = description
            )
        }
    }
}