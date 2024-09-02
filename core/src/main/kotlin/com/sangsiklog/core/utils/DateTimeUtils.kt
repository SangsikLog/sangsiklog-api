package com.sangsiklog.core.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object DateTimeUtils {
    fun LocalDateTime.toUnixTimestampInMilliseconds(): Long {
        return this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    fun milliToLocalDateTime(milli: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milli), ZoneId.systemDefault())
    }
}