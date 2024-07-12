package com.sangsiklog.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
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