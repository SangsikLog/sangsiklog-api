package com.sangsiklog.config.interceptor

import org.springframework.context.annotation.Configuration
import org.springframework.graphql.server.WebGraphQlInterceptor
import org.springframework.graphql.server.WebGraphQlRequest
import org.springframework.graphql.server.WebGraphQlResponse
import reactor.core.publisher.Mono

@Configuration
class UserInterceptor: WebGraphQlInterceptor {

    override fun intercept(request: WebGraphQlRequest, chain: WebGraphQlInterceptor.Chain): Mono<WebGraphQlResponse> {
        val userId = request.headers.getFirst("X-USER-ID")
        val userRole = request.headers.getFirst("X-USER-ROLE")

        request.configureExecutionInput { executionInput, executionInputBuilder ->
            executionInput.graphQLContext.put("X-USER-ID", userId?:"-1")
            executionInput.graphQLContext.put("X-USER-ROLE", userRole?:"user")

            executionInput
        }

        return chain.next(request)
    }
}