package com.sangsiklog.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.sangsiklog.model.ApiResponse
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
@RequiredArgsConstructor
class HttpClient(
    private val objectMapper: ObjectMapper,
    private val restTemplate: RestTemplate
) {
    fun <T> get(url: String, responseType: Class<T>, queryParams: Map<String, Any>? = null): T? {
        val uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
        queryParams?.forEach { (key, value) ->
            uriBuilder.queryParam(key, value)
        }
        val serviceUrl = uriBuilder.toUriString()

        val json = restTemplate.getForObject(serviceUrl, String::class.java)
        val apiResponse = objectMapper.readValue<ApiResponse<Map<String, Any>>>(json!!)

        return objectMapper.convertValue(apiResponse.result, responseType)
    }

    fun <T> post(url: String, request: Any, responseType: Class<T>): T? {
        val json = restTemplate.postForObject(url, request, String::class.java)
        val apiResponse = objectMapper.readValue<ApiResponse<Map<String, Any>>>(json!!)

        return objectMapper.convertValue(apiResponse.result, responseType)
    }

    fun put(url: String, request: Any) {
        restTemplate.put(url, request)
    }

    fun delete(url: String) {
        restTemplate.delete(url)
    }
}