package com.sangsiklog.handler

import com.sangsiklog.core.api.exception.CustomException
import com.sangsiklog.core.api.response.ApiErrorResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*


@RestControllerAdvice(basePackages = ["com.sangsiklog.controller"])
class ApiExceptionHandler: ResponseEntityExceptionHandler() {

    private val messageMappings: Map<Class<out Exception?>, String> = Collections.unmodifiableMap(
        linkedMapOf(
            Pair(MethodArgumentNotValidException::class.java, "Request body is invalid")
        )
    )

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val responseBody = ApiErrorResponse(
            code = status.value(),
            message = resolveMessage(ex, ex.message),
            details = getDetails(ex)
        )

        return super.handleExceptionInternal(ex, responseBody, headers, status, request)
    }

    override fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val responseBody = ApiErrorResponse(
            code = status.value(),
            message = ex.message,
            details = null
        )

        return super.handleExceptionInternal(ex, responseBody, headers, status, request)
    }

    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val responseBody = ApiErrorResponse(
            code = statusCode.value(),
            message = ex.message,
            details = body
        )

        return super.handleExceptionInternal(ex, responseBody, headers, statusCode, request)
    }

    @ExceptionHandler
    fun handleCustomException(ex: CustomException, request: WebRequest): ResponseEntity<Any>? {
        val responseBody = ApiErrorResponse(
            code = ex.errorType.code,
            message = ex.errorType.message,
            details = null
        )

        return super.handleExceptionInternal(ex, responseBody, HttpHeaders(), ex.status, request)
    }

    private fun resolveMessage(ex: Exception, defaultMessage: String): String {
        return messageMappings.entries
            .firstOrNull { entry -> entry.key.isAssignableFrom(ex.javaClass) }
            ?.value ?: defaultMessage
    }

    private fun getDetails(ex: BindException): List<HashMap<String, String?>> {
        return when (ex) {
            is MethodArgumentNotValidException -> {
                ex.bindingResult.globalErrors.map { error ->
                    hashMapOf(Pair("target", error.objectName), Pair("message", error.defaultMessage))
                }.toList()
            }
            else -> {
                ex.bindingResult.fieldErrors.map { error ->
                    hashMapOf(Pair("target", error.field), Pair("message", error.defaultMessage))
                }.toList()
            }
        }
    }
}