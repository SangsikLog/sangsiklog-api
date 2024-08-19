package com.sangsiklog.gateway.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/fallback")
class FallbackController {
    @GetMapping("/authFailure")
    fun authFailure(): Mono<Map<String, String>> {
        return Mono.just(mapOf(Pair("status", "down")))
    }
}