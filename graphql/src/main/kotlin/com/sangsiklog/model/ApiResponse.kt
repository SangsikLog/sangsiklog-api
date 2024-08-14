package com.sangsiklog.model

data class ApiResponse<T> (
    val code: Int,
    val message: String? = null,
    val result: T? = null
)