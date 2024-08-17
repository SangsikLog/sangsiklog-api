package com.sangsiklog.service.auth

import com.sangsiklog.core.api.exception.ErrorType
import com.sangsiklog.core.log.logger
import com.sangsiklog.domain.user.User
import com.sangsiklog.exception.auth.AuthServiceException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class AuthService(
    private val passwordEncoder: PasswordEncoder
) {

    @Value("\${jwt.secret}")
    lateinit var secretKey: String


    fun generateToken(existingUser: User, requestPassword: String): String {
        if (!passwordEncoder.matches(requestPassword, existingUser.password)) {
            throw AuthServiceException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_CREDENTIALS)
        }

        val currentTimeMillis = System.currentTimeMillis()
        return Jwts.builder()
            .subject(existingUser.email)
            .issuedAt(Date(currentTimeMillis))
            .expiration(Date(currentTimeMillis + 3600000))
            .signWith(Keys.hmacShaKeyFor(secretKey.toByteArray(Charsets.UTF_8)))
            .compact()
    }

    fun verifyToken(token: String): Boolean {
        try {
            parseJwtClaims(token)
            return true
        } catch (e: Exception) {
            logger.error("validateToken error : ${e.message}")
            return false
        }
    }

    fun refreshToken(token: String): String {
        try {
            val claims = parseJwtClaims(token)
            val currentTimeMillis = System.currentTimeMillis()
            return Jwts.builder()
                .subject(claims.subject)
                .issuedAt(Date(currentTimeMillis))
                .expiration(Date(currentTimeMillis + 3600000))
                .signWith(Keys.hmacShaKeyFor(secretKey.toByteArray(Charsets.UTF_8)))
                .compact()
        } catch (e: Exception) {
            throw AuthServiceException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_CREDENTIALS)
        }
    }

    fun parseJwtClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(secretKey.toByteArray(Charsets.UTF_8)))
            .build()
            .parseSignedClaims(token)
            .payload
    }

}