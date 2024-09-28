package com.sangsiklog.exception

class UnauthorizedException(
    override val message: String
): RuntimeException(message)