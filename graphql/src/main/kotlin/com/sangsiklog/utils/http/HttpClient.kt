package com.sangsiklog.utils.http

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.sangsiklog.exception.HttpServiceException
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
@RequiredArgsConstructor
class HttpClient(
    private val objectMapper: ObjectMapper,
    private val restTemplate: RestTemplate
) {
    fun <T> get(url: String, responseType: Class<T>, queryParams: Map<String, Any>? = null): T? {
        try {
            val uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
            queryParams?.forEach { (key, value) ->
                uriBuilder.queryParam(key, value)
            }
            val serviceUrl = uriBuilder.toUriString()

            val json = restTemplate.getForObject(serviceUrl, String::class.java)
            val apiResponse = objectMapper.readValue<ApiResponse<Map<String, Any>>>(json!!)

            return objectMapper.convertValue(apiResponse.result, responseType)
        } catch (e: HttpStatusCodeException) {
            throw HttpServiceException(e.responseBodyAsString)
        }
    }

    fun <T> post(url: String, request: Any, responseType: Class<T>): T? {
        try {
            val json = restTemplate.postForObject(url, request, String::class.java)
            val apiResponse = objectMapper.readValue<ApiResponse<Map<String, Any>>>(json!!)

            return objectMapper.convertValue(apiResponse.result, responseType)
        } catch (e: HttpStatusCodeException) {
            throw HttpServiceException(e.responseBodyAsString)
        }
    }

    fun put(url: String, request: Any) {
        try {
            restTemplate.put(url, request)
        } catch (e: HttpStatusCodeException) {
            throw HttpServiceException(e.responseBodyAsString)
        }
    }

    fun delete(url: String) {
        try {
            restTemplate.delete(url)
        } catch (e: HttpStatusCodeException) {
            throw HttpServiceException(e.responseBodyAsString)
        }
    }
}