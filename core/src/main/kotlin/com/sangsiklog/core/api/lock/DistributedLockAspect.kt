package com.sangsiklog.core.api.lock

import com.sangsiklog.core.api.exception.DistributedLockException
import com.sangsiklog.core.api.exception.ErrorType
import com.sangsiklog.core.log.logger
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.util.*

@Aspect
@Component
class DistributedLockAspect(
    private val distributedLockService: DistributedLockService,
) {
    private val expressionParser = SpelExpressionParser()

    @Around("@annotation(distributedLock)")
    @Throws(Throwable::class)
    fun lock(joinPoint: ProceedingJoinPoint, distributedLock: DistributedLock): Any? {
        val signature = joinPoint.signature as MethodSignature
        val lockKey = generateLockKey(distributedLock.value, distributedLock.keys, signature.parameterNames, joinPoint.args)
        val lockValue = UUID.randomUUID().toString()

        val lockAcquired = distributedLockService.acquireLock(lockKey, lockValue)
        if (!lockAcquired) {
            logger.error { "Unable to acquire lock for key: $lockKey" }
            throw DistributedLockException(HttpStatus.LOCKED, ErrorType.CAN_NOT_ACQUIRE_LOCK)
        }

        try {
            return joinPoint.proceed()
        } finally {
            val result = distributedLockService.releaseLock(lockKey, lockValue)
            if (!result) {
                logger.error { "Failed to release lock for key: $lockKey with value: $lockValue" }
                throw DistributedLockException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_LOCK_KEY_VALUE)
            }
        }
    }

    private fun generateLockKey(lockType: LockType, keys: Array<String>, parameterNames: Array<String>, args: Array<Any>): String {
        val context = StandardEvaluationContext().apply {
            parameterNames.forEachIndexed { index, _ ->
                setVariable(parameterNames[index], args[index])
            }
        }

        val keyString = keys
            .map { key -> expressionParser.parseExpression(key).getValue(context)?.toString().orEmpty() }
            .filter { it.isNotEmpty() }
            .joinToString(":")

        if (keyString.isEmpty()) {
            logger.error { "[DistributedLock] key is null or empty LockType: ${lockType}, keys: $keys" }
            throw DistributedLockException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_LOCK_KEY_VALUE)
        }

        return "${lockType.prefix}:$keyString"
    }
}