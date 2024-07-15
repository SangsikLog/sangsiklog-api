package com.sangsiklog.domain

import com.sangsiklog.domain.base.BaseEntity
import jakarta.persistence.*
import lombok.Getter
import lombok.NoArgsConstructor

@Getter
@Entity
@NoArgsConstructor
@SequenceGenerator(
    name = "KNOWLEDGE_SEQ_GENERATOR",
    sequenceName = "KNOWLEDGE_SEQ",
    initialValue = 1,
    allocationSize = 50
)
@Table(name = "knowledge")
class Knowledge(
    id: Long,
    title: String,
    description: String
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KNOWLEDGE_SEQ_GENERATOR")
    @Column(name = "knowledge_id")
    var id: Long = id
        protected set

    @Column(nullable = false)
    var title: String = title
        protected set

    @Column(nullable = false)
    var description: String = description
        protected set
}