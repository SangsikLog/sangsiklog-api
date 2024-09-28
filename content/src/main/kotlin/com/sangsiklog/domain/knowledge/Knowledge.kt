package com.sangsiklog.domain.knowledge

import com.sangsiklog.domain.base.BaseEntity
import com.sangsiklog.domain.category.Category
import com.sangsiklog.domain.listener.KnowledgeListener
import jakarta.persistence.*

@Entity
@EntityListeners(KnowledgeListener::class)
@SequenceGenerator(
    name = "KNOWLEDGE_SEQ_GENERATOR",
    sequenceName = "KNOWLEDGE_SEQ",
    initialValue = 1,
    allocationSize = 50
)
@Table(name = "knowledge", indexes = [Index(name = "idx_knowledge_category", columnList = "knowledge_id, category_id")])
class Knowledge(
    userId: Long,
    title: String,
    description: String,
    category: Category,
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false, foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    var category: Category = category
        protected set

    companion object {
        fun create(userId: Long, title: String, description: String, category: Category): Knowledge {
            return Knowledge(
                userId = userId,
                title = title,
                description = description,
                category = category
            )
        }
    }
}