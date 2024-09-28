package com.sangsiklog.exception.auth

import com.sangsiklog.core.api.exception.CustomException
import com.sangsiklog.core.api.exception.ErrorType
import org.springframework.http.HttpStatus

class EmailServiceException(
    status: HttpStatus,
    errorType: ErrorType
): CustomException(status, errorType)