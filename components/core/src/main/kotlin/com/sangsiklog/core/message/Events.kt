package com.sangsiklog.core.message

import com.fasterxml.jackson.databind.ObjectMapper
import com.sangsiklog.core.log.logger
import org.springframework.transaction.support.TransactionSynchronizationManager

private val eventsThreadLocal: ThreadLocal<MutableList<DomainEvent>> = ThreadLocal.withInitial { mutableListOf() }

fun register(event: DomainEvent?) {
    val objectMapper = ObjectMapper()

    if (event == null) {
        throw IllegalArgumentException("domain event cannot be null")
    }

    if (!TransactionSynchronizationManager.isActualTransactionActive()) {
        logger.error("[DOMAIN EVENT] ${event.javaClass.simpleName} is registered without transaction " +
                "(payload : ${objectMapper.writeValueAsString(event)})")
        throw UnsupportedOperationException("Cannot register domain event. Transaction is not active")
    }

    eventsThreadLocal.get().add(event)
}

fun getDomainEvents(): MutableList<DomainEvent> {
    return eventsThreadLocal.get()
}

fun clear() {
    eventsThreadLocal.get().clear()
}