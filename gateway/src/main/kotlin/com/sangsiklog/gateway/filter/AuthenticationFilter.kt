package com.sangsiklog.gateway.filter

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthenticationFilter(
    lbFunction: ReactorLoadBalancerExchangeFilterFunction
): AbstractGatewayFilterFactory<AuthenticationFilter.Config>(Config::class.java) {

    @LoadBalanced
    private var webClient: WebClient = WebClient.builder()
        .filter(lbFunction)
        .baseUrl("http://auth-api")
        .build()

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val authHeader = exchange.request.headers.getFirst("Authorization")
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                val token = authHeader.removePrefix("Bearer ")
                validateToken(token)
                    .flatMap { userId -> proceedWithUserId(userId, exchange, chain) }
                    .switchIfEmpty(chain.filter(exchange))
                    .onErrorResume { handleAuthenticationError(exchange) }
            } else {
                chain.filter(exchange)
            }
        }
    }

    private fun validateToken(token: String): Mono<Long> {
        return webClient.post()
            .uri("/v1/auth/validate")
            .bodyValue(mapOf("token" to token))
            .header("Content-Type", "application/json")
            .retrieve()
            .bodyToMono(ValidateTokenResponse::class.java)
            .map { response -> response.result.id }
    }

    private fun proceedWithUserId(userId: Long, exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        exchange.request.mutate().header("X-USER-ID", userId.toString())
        return chain.filter(exchange)
    }

    private fun handleAuthenticationError(exchange: ServerWebExchange): Mono<Void> {
        exchange.response.statusCode = HttpStatus.UNAUTHORIZED
        return exchange.response.setComplete()
    }

    class Config
}