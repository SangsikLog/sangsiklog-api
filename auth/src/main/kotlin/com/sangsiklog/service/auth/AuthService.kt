package com.sangsiklog.service.auth

import com.sangsiklog.core.api.exception.ErrorType
import com.sangsiklog.core.log.logger
import com.sangsiklog.domain.user.User
import com.sangsiklog.exception.auth.AuthServiceException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.concurrent.TimeUnit

@Service
@Transactional(readOnly = true)
class AuthService(
    private val passwordEncoder: PasswordEncoder,
    private val emailService: EmailService,
    private val redisTemplate: RedisTemplate<String, Any>
) {

    @Value("\${jwt.secret}")
    lateinit var secretKey: String

    companion object {
        private const val EMAIL_VERIFY_TOKEN_EXPIRE_TIME = 5L
        private const val VERIFIED_EMAIL_EXPIRE_TIME = 10L
    }

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

    fun sendVerificationMail(email: String) {
        val token = UUID.randomUUID().toString()

        redisTemplate.opsForValue().set("token:$email", token, EMAIL_VERIFY_TOKEN_EXPIRE_TIME, TimeUnit.MINUTES)

        emailService.sendEmail(
            email,
            "[상식로그] 이메일 인증번호를 확인해주세요!",
            "인증번호: $token"
        )
    }

    fun verifyEmailToken(email:String, token: String) {
        val storedToken = redisTemplate.opsForValue().get("token:$email")
        if (storedToken == null || storedToken != token) {
            throw AuthServiceException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_EMAIL_TOKEN)
        }

        redisTemplate.opsForValue().set("verified:$email", "true", VERIFIED_EMAIL_EXPIRE_TIME, TimeUnit.MINUTES)

        redisTemplate.delete("token:$email")
    }

    fun isEmailVerified(email: String): Boolean {
        val result = redisTemplate.opsForValue().get("verified:$email")
        return result?.let { it == "true" } ?: throw AuthServiceException(HttpStatus.BAD_REQUEST, ErrorType.NOT_VERIFIED_EMAIL)
    }
}