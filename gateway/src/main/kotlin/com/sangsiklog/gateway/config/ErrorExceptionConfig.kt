package com.sangsiklog.gateway.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.sangsiklog.gateway.exception.GlobalExceptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ErrorExceptionConfig(
    private val objectMapper: ObjectMapper
) {

    @Bean
    fun globalExceptionHandler(): GlobalExceptionHandler {
        return GlobalExceptionHandler(objectMapper)
    }
}