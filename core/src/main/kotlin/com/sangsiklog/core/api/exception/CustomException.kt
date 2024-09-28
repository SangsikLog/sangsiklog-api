package com.sangsiklog.core.api.exception

import org.springframework.http.HttpStatus

open class CustomException(
    val status: HttpStatus,
    val errorType: ErrorType
): RuntimeException()