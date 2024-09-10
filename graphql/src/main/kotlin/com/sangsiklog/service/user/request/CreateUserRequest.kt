package com.sangsiklog.service.user.request

data class CreateUserRequest(
    val nickname: String,
    val email: String,
    val password: String
)
