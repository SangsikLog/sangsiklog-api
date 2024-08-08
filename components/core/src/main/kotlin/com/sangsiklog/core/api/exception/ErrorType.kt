package com.sangsiklog.core.api.exception

enum class ErrorType(
    val code: Int,
    val message: String
) {
    // 1000~1999 UserServiceException
    NOT_FOUND_USER(1000, "사용자를 찾을 수 없습니다."),
    INVALID_OLD_PASSWORD(1001, "기존 비밀번호가 잘못되었습니다."),

    // 2000~2999 AuthServiceException
    INVALID_CREDENTIALS(2000, "유효하지 않은 자격증명"),
}