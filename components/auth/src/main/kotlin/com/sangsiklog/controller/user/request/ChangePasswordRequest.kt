package com.sangsiklog.controller.user.request

data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)
