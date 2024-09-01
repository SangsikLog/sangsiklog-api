package com.sangsiklog.core.api.exception

open class GrpcCustomException(
    open val errorType: ErrorType
): RuntimeException(errorType.message)