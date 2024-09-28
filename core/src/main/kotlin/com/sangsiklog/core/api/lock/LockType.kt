package com.sangsiklog.core.api.lock

enum class LockType(val prefix: String) {
    CREATE_USER("CREATE_USER"),
    UPDATE_USER("UPDATE_USER"),
    CHANGE_PASSWORD_USER("CHANGE_PASSWORD")
}