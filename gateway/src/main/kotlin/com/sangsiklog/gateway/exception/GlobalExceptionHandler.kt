package com.sangsiklog.gateway.exception

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Order(Ordered.HIGHEST_PRECEDENCE)
class GlobalExceptionHandler(
    private val objectMapper: ObjectMapper
): ErrorWebExceptionHandler {
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        val response = exchange.response
        if (response.isCommitted) {
            return Mono.error(ex)
        }

        response.headers.contentType = MediaType.APPLICATION_JSON
        if (ex is ResponseStatusException) {
            response.statusCode = ex.statusCode
        }

        return response.writeWith(Mono.fromSupplier {
            val bufferFactory = response.bufferFactory()
            try {
                val globalErrorResponse = GlobalErrorResponse.defaultBuild(ex.message)
                val errorResponse = objectMapper.writeValueAsBytes(globalErrorResponse)

                bufferFactory.wrap(errorResponse)
            } catch (e: Exception) {
                bufferFactory.wrap(byteArrayOf())
            }
        })
    }

    class GlobalErrorResponse(
        val errormessage: String? = "",
        val localDateTime: LocalDateTime,
        val additionalInfos: Map<String, Any>? = emptyMap()
    ) {
        companion object {
            fun defaultBuild(errormessage: String?): GlobalErrorResponse {
                return GlobalErrorResponse(errormessage, LocalDateTime.now())
            }
        }
    }
}