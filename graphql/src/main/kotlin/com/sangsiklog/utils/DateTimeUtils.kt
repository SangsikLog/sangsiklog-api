package com.sangsiklog.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class DateTimeUtils {
    companion object {
        fun milliToLocalDateTime(milli: Long): LocalDateTime {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(milli), ZoneId.systemDefault())
        }
    }
}