package com.sangsiklog.domain.knowledge

import com.sangsiklog.core.domain.base.BaseEntity
import com.sangsiklog.core.utils.DateTimeUtils.toUnixTimestampInMilliseconds
import com.sangsiklog.service.knowledge.KnowledgeServiceOuterClass.KnowledgeDetail
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

    fun convertProto(): KnowledgeDetail {
        return KnowledgeDetail.newBuilder()
            .setKnowledgeId(this.id!!)
            .setTitle(this.title)
            .setDescription(this.description)
            .setCreatedAt(this.createdAt!!.toUnixTimestampInMilliseconds())
            .build()
    }
}