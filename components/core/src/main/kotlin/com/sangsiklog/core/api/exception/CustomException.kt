package com.sangsiklog.core.api.exception

import org.springframework.http.HttpStatus

data class CustomException(
    val status: HttpStatus,
    val errorType: ErrorType
): Exception()