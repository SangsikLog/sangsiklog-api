package com.sangsiklog.knowledge.config.redis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "spring.data.redis.single")
class RedisSingleConfigProperties {
    lateinit var address: String
}