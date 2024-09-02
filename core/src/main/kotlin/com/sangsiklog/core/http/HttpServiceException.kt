package com.sangsiklog.core.http

class HttpServiceException(
    override val message: String
): RuntimeException(message)