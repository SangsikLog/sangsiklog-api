package com.sangsiklog.core.api.lock

import com.sangsiklog.core.log.logger
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class DistributedLockService(
    private val redisTemplate: RedisTemplate<String, Any>
) {
    companion object {
        const val DEFAULT_LOCK_TIMEOUT = 5L
    }

    fun acquireLock(lockKey: String, lockValue: String, timeout: Long = DEFAULT_LOCK_TIMEOUT): Boolean {
        logger.info { "acquiredLock($lockKey, $lockValue)" }
        return redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, timeout, TimeUnit.SECONDS) == true
    }

    fun releaseLock(lockKey: String, lockValue: String): Boolean {
        val script = """
            if redis.call('GET', KEYS[1]) == ARGV[1] then
                return redis.call('DEL', KEYS[1])
            else
                return 0
            end
        """.trimIndent()

        val result = redisTemplate.execute(
            DefaultRedisScript(script, Long::class.java),
            listOf(lockKey),
            lockValue
        )

        logger.info { "releaseLock($lockKey, $lockValue)" }

        return result == 1L
    }
}