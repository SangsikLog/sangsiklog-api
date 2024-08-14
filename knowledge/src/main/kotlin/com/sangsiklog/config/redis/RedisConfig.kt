package com.sangsiklog.config.redis

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(RedisSingleConfigProperties::class)
class RedisConfig {
    @Autowired
    lateinit var redisSingleConfigProperties: RedisSingleConfigProperties

    @Bean(destroyMethod = "shutdown")
    fun redissonClient(): RedissonClient {
        val config = Config()
        config.useSingleServer()
            .setAddress(redisSingleConfigProperties.address)
            .setPassword("sangsiklog")

        return Redisson.create(config)
    }
}