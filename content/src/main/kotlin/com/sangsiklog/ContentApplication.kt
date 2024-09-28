package com.sangsiklog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
@EnableDiscoveryClient
class ContentApplication

fun main(args: Array<String>) {
    runApplication<ContentApplication>(*args)
}
