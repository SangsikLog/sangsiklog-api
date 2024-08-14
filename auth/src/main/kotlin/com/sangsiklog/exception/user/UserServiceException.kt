package com.sangsiklog.exception.user

import com.sangsiklog.core.api.exception.CustomException
import com.sangsiklog.core.api.exception.ErrorType
import org.springframework.http.HttpStatus

class UserServiceException(
    status: HttpStatus,
    errorType: ErrorType
): CustomException(status, errorType)