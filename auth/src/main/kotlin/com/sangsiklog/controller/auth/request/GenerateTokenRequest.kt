package com.sangsiklog.controller.auth.request

data class GenerateTokenRequest(
    val email: String = "",
    val password: String = ""
)
