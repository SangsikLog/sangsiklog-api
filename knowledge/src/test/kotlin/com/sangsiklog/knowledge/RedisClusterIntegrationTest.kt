package com.sangsiklog.knowledge

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.redisson.api.RBucket
import org.redisson.api.RedissonClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class RedisClusterIntegrationTest {

    @Autowired
    lateinit var redissonClient: RedissonClient

    @Test
    fun testRedisConnection() {
        // Test setting a value in Redis
        val bucket: RBucket<String> = redissonClient.getBucket("testKey")
        bucket.set("testValue")

        // Test getting the value from Redis
        val value = bucket.get()
        assertEquals("testValue", value)
    }
}