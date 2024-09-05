package com.sangsiklog.core.api.exception

import org.springframework.http.HttpStatus

class DistributedLockException(
    status: HttpStatus,
    errorType: ErrorType
): CustomException(status, errorType)