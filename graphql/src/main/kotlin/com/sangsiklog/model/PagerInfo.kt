package com.sangsiklog.model

import common.Common

class PagerInfo(
    val totalCount: Long = 0
)  {
    companion object {
        fun fromProto(proto: Common.PagerInfo): PagerInfo {
            return PagerInfo(
                totalCount = proto.totalCount
            )
        }
    }
}