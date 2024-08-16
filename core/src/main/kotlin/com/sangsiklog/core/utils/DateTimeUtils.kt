package com.sangsiklog.core.utils

import java.time.LocalDateTime
import java.time.ZoneId

object DateTimeUtils {
    fun LocalDateTime.toUnixTimestampInMilliseconds(): Long {
        return this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}