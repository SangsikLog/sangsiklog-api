package com.sangsiklog.auth.controller.user.request

data class CreateUserRequest(
    val name: String,
    val email: String,
    val password: String
)
