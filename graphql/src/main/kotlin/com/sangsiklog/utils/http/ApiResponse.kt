package com.sangsiklog.utils.http

data class ApiResponse<T> (
    val code: Int = 0,
    val message: String? = null,
    val result: T? = null
)