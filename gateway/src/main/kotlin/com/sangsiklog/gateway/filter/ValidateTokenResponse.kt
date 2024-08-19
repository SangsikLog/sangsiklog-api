package com.sangsiklog.gateway.filter

data class ValidateTokenResponse(
    val code: Int,
    val message: String,
    val result: ValidateTokenResult
)

data class ValidateTokenResult(
    val id: Long
)