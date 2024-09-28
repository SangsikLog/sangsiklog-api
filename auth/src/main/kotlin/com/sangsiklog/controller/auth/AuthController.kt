package com.sangsiklog.controller.auth

import com.sangsiklog.controller.auth.request.*
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

    @PostMapping("/email")
    fun sendVerificationMail(@RequestBody request: EmailVerificationRequest): String {
        authService.sendVerificationMail(request.email)

        return "ok"
    }

    @PostMapping("/email/verify-token")
    fun verifyEmailToken(@RequestBody request: VerifyEmailTokenRequest): String {
        authService.verifyEmailToken(request.email, request.token)

        return "ok"
    }
}