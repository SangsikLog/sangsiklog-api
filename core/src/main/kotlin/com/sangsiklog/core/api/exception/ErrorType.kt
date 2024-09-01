package com.sangsiklog.core.api.exception

enum class ErrorType(
    val code: Int,
    val message: String
) {
    // 1000~1999 UserServiceException
    NOT_FOUND_USER(1000, "사용자를 찾을 수 없습니다."),
    INVALID_OLD_PASSWORD(1001, "기존 비밀번호가 잘못되었습니다."),
    EMAIL_ALREADY_EXISTS(1002, "이미 존재하는 이메일 입니다."),

    // 2000~2999 AuthServiceException
    INVALID_CREDENTIALS(2000, "유효하지 않은 자격증명"),

    // 3000~3999 KnowledgeServiceException
    NOT_FOUND_KNOWLEDGE(3000, "상식을 찾을 수 없습니다."),

    // default
    UNKNOWN(9999, "알 수 없는 에러"),
    PAGE_INDEX_ERROR(10000, "페이지는 1 보다 작을 수 없습니다."),
}