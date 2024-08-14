package com.sangsiklog.command.controller

import com.sangsiklog.core.log.logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/command")
class CommandTestController {

    @GetMapping
    fun testLogging(): String {
        logger.info("command controller test logging called")
        return "testLogging"
    }
}