package com.sangsiklog.core.api.response

data class ApiResponse<T> (
    val code: Int,
    val message: String? = null,
    val result: T? = null
)