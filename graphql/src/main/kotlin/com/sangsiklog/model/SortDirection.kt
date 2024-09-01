package com.sangsiklog.model

import common.Enum

enum class SortDirection {
    ASC,
    DESC;

    fun toProto() : Enum.SortDirection {
        return Enum.SortDirection.valueOf(this.name)
    }
}