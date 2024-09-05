package com.sangsiklog.core.api.lock

enum class LockType(val prefix: String) {
    CREATE_USER("CREATE_USER"),
    REGISTER_KNOWLEDGE("REGISTER_KNOWLEDGE")
}