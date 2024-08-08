package com.sangsiklog.controller.auth.request

import com.fasterxml.jackson.annotation.JsonProperty

data class VerifyTokenRequest(
    @JsonProperty("token") val token: String
)
