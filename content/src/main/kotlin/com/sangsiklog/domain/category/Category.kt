package com.sangsiklog.domain.category

import com.sangsiklog.domain.base.BaseEntity
import com.sangsiklog.domain.knowledge.Knowledge
import jakarta.persistence.*

@Entity
@SequenceGenerator(
    name = "CATEGORY_SEQ_GENERATOR",
    sequenceName = "CATEGORY_SEQ",
    initialValue = 1,
    allocationSize = 50
)
@Table(name = "category", indexes = [Index(name = "idx_category", columnList = "category_id")])
class Category(
    name: String
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATEGORY_SEQ_GENERATOR")
    @Column(name = "category_id")
    val id: Long? = null

    @Column(name = "title", nullable = false)
    var name: String = name
        protected set

    @OneToMany(mappedBy = "category")
    var knowledgeList: MutableSet<Knowledge> = HashSet()
        protected set

    companion object {
        fun create(name: String): Category {
            return Category(
                name = name
            )
        }
    }
}