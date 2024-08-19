package com.sangsiklog.controller.auth

import com.sangsiklog.controller.auth.request.GenerateTokenRequest
import com.sangsiklog.controller.auth.request.RefreshTokenRequest
import com.sangsiklog.controller.auth.request.ValidateTokenRequest
import com.sangsiklog.controller.auth.request.VerifyTokenRequest
import com.sangsiklog.controller.auth.response.GenerateTokenResponse
import com.sangsiklog.controller.auth.response.RefreshTokenResponse
import com.sangsiklog.controller.auth.response.ValidateTokenResponse
import com.sangsiklog.controller.auth.response.VerifyTokenResponse
import com.sangsiklog.service.auth.AuthService
import com.sangsiklog.service.user.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val authService: AuthService,
    private val userService: UserService
) {
    @PostMapping("/token")
    fun generateToken(servletRequest: HttpServletRequest,
                      @RequestBody request: GenerateTokenRequest): GenerateTokenResponse {
        val ipAddress = servletRequest.remoteAddr
        val user = userService.getUserByEmail(request.email)
        userService.createUserLoginHistory(user, ipAddress)

        return GenerateTokenResponse(
            token = authService.generateToken(user, request.password)
        )
    }

    @PostMapping("/verify-token")
    fun verifyToken(@RequestBody request: VerifyTokenRequest): VerifyTokenResponse {
        return VerifyTokenResponse(
            isValid = authService.verifyToken(request.token)
        )
    }

    @PostMapping("/refresh-token")
    fun refreshToken(@RequestBody request: RefreshTokenRequest): RefreshTokenResponse {
        return RefreshTokenResponse(
            token = authService.refreshToken(request.token)
        )
    }

    @PostMapping("/validate")
    fun validateToken(@RequestBody request: ValidateTokenRequest): ValidateTokenResponse {
        val claims = authService.parseJwtClaims(request.token)
        val user = userService.getUserByEmail(claims.subject)
        return ValidateTokenResponse(
            id = user.id!!
        )
    }
}