package com.sangsiklog.exception

class HttpServiceException(
    override val message: String
): RuntimeException(message)