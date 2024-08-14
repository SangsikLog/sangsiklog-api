package com.sangsiklog.core.api.response

data class ApiErrorResponse<T> (
    val code: Int,
    val message: String? = null,
    val details: T? = null
)