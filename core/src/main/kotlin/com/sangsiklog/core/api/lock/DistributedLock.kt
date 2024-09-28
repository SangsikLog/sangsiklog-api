package com.sangsiklog.core.api.lock

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DistributedLock(
    val value: LockType,
    val keys: Array<String>
)
