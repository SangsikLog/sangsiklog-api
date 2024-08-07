package com.sangsiklog.controller.user.response

data class UserDetailsResponse(
    val id: Long,
    val name: String,
    val profileImageUrl: String?,
    val email: String
)
