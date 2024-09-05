package com.sangsiklog.controller.auth.request

data class VerifyEmailTokenRequest(
    val email: String = "",
    val token: String = ""
)
