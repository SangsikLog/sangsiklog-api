package com.sangsiklog.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [RedisAutoConfiguration::class])
class AuthApplication

fun main(args: Array<String>) {
    runApplication<AuthApplication>(*args)
}
