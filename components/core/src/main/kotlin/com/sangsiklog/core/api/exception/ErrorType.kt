package com.sangsiklog.core.api.exception

enum class ErrorType(
    val code: Int,
    val message: String
) {
    // 1000~2000 UserServiceException
    NOT_FOUND_USER(1000, "사용자를 찾을 수 없습니다."),
    INVALID_OLD_PASSWORD(1001, "기존 비밀번호가 잘못되었습니다.")
}