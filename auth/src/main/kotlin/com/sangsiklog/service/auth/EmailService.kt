package com.sangsiklog.service.auth

import com.sangsiklog.core.api.exception.ErrorType
import com.sangsiklog.exception.auth.EmailServiceException
import jakarta.mail.internet.AddressException
import jakarta.mail.internet.InternetAddress
import org.springframework.http.HttpStatus
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val mailSender: JavaMailSender
) {
    fun sendEmail(to: String, subject: String, text: String) {
        validateEmail(to)

        val message = SimpleMailMessage().apply {
            setTo(to)
            setSubject(subject)
            setText(text)
        }
        mailSender.send(message)
    }

    private fun validateEmail(email: String) {
        try {
            InternetAddress(email).apply {
                validate()
            }
        } catch (e: AddressException) {
            throw EmailServiceException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_EMAIL_ADDRESS)
        }
    }
}