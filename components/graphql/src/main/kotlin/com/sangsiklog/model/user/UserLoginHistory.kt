package com.sangsiklog.model.user

import java.time.LocalDateTime

data class UserLoginHistory(
    val id: Long,
    val userId: Long,
    val loginTime: LocalDateTime,
    val ipAddress: String
)
