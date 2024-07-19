package com.sangsiklog.core.api.exception

enum class ErrorType(
    val code: Int,
    val message: String
) {
    TEST_ERROR_TYPE(0, "Test error type")
}