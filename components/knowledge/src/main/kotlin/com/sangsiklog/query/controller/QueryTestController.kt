package com.sangsiklog.query.controller

import com.sangsiklog.core.log.logger
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
}