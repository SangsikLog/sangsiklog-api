package com.sangsiklog.core.api.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.sangsiklog.core.api.response.ApiErrorResponse
import com.sangsiklog.core.api.response.ApiResponse
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@RestControllerAdvice(basePackages = ["com.sangsiklog.controller"])
class ResponseHandler: ResponseBodyAdvice<Any> {
    private val objectMapper = ObjectMapper()

    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        return true
    }

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {
        return when (body) {
            is ApiErrorResponse<*> -> {
                body
            }
            is String -> {
                response.headers.contentType = MediaType.APPLICATION_JSON
                objectMapper.writeValueAsString(ApiResponse(HttpStatus.OK.value(), "success", body))
            }
            else -> {
                ApiResponse(HttpStatus.OK.value(), "success", body)
            }
        }
    }
}