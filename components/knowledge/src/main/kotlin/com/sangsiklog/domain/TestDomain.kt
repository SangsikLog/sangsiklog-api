package com.sangsiklog.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import java.time.LocalDateTime

@Entity
@SequenceGenerator(name = "TEST_SEQ_GENERATOR", sequenceName = "TEST_SEQ", initialValue = 1, allocationSize = 50)
class TestDomain(
    id: Long,
    createdAt: LocalDateTime
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEST_SEQ_GENERATOR")
    @Column(name = "test_id")
    var id: Long = id
        private set

    @Column(nullable = false)
    var createdAt: LocalDateTime = createdAt
        private set
}