package com.sangsiklog.exception

import com.sangsiklog.core.api.exception.ErrorType

class ErrorStatus(
    val errorType: ErrorType,
    val message: String
)