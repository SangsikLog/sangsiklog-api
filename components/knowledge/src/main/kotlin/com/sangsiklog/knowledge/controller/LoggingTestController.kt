package com.sangsiklog.knowledge.controller

import com.sangsiklog.core.log.logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class LoggingTestController {

    @GetMapping
    fun testLogging(): String {
        logger.info("Test logging called")
        return "testLogging"
    }
}