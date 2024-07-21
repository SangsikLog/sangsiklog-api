package com.sangsiklog.core.message

import java.time.LocalDateTime
import java.util.concurrent.CopyOnWriteArrayList

abstract class DomainEvent(
    val aggregateId: String,
    val eventVersion: Int = 1,
    val occurredOn: LocalDateTime = LocalDateTime.now(),
    val route: MutableList<String> = CopyOnWriteArrayList()
)