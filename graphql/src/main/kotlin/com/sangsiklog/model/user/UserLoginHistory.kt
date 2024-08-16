package com.sangsiklog.model.user

data class UserLoginHistory(
    val id: Long = 0,
    val userId: Long = 0,
    val loginTime: String = "",
    val ipAddress: String = ""
)
