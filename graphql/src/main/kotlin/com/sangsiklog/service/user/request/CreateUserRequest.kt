package com.sangsiklog.service.user.request

data class CreateUserRequest(
    val name: String,
    val email: String,
    val password: String
)
