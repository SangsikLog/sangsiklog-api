package com.sangsiklog.auth.controller

import com.sangsiklog.core.log.logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthTestController {

    @GetMapping("/auth")
    fun authTest(): ResponseEntity<String> {
        logger.info("AUTH TEST")
        return ResponseEntity.ok("AUTH API OK");
    }
}