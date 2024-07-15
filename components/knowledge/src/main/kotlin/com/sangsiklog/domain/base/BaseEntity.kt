package com.sangsiklog.domain.base

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import lombok.Getter
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime


@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
    @CreatedDate
    var createdAt: LocalDateTime? = null
        protected set

    @LastModifiedDate
    var modifiedAt: LocalDateTime? = null
        protected set
}