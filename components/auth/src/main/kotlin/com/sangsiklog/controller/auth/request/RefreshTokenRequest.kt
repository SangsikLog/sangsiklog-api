package com.sangsiklog.controller.auth.request

import com.fasterxml.jackson.annotation.JsonProperty

data class RefreshTokenRequest(
    @JsonProperty("token") val token: String
)
