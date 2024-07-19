package com.sangsiklog.query.controller

import com.sangsiklog.core.api.exception.CustomException
import com.sangsiklog.core.api.exception.ErrorType
import com.sangsiklog.core.log.logger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/query")
class QueryTestController {

    @GetMapping
    fun testLogging(): String {
        logger.info("query controller test logging called")
        return "testLogging"
    }

    @GetMapping("/exception")
    fun testExceptionResponse(): String {
        logger.info("query controller test logging called")

        throw CustomException(HttpStatus.BAD_REQUEST, ErrorType.TEST_ERROR_TYPE)

        return "testLogging"
    }
}