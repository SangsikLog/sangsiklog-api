package com.sangsiklog.utils

import com.sangsiklog.core.api.exception.ErrorType
import com.sangsiklog.exception.knowledge.KnowledgeServiceException
import common.Common
import common.Enum
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

class PageableUtil {
    companion object {
        fun createByProto(pageable: Common.Pageable): Pageable {
            if (pageable.page < 1) {
                throw KnowledgeServiceException(ErrorType.PAGE_INDEX_ERROR)
            }
            val sort = if (pageable.direction == Enum.SortDirection.ASC) {
                Sort.by(pageable.sortColumn).ascending()
            } else {
                Sort.by(pageable.sortColumn).descending()
            }

            return PageRequest.of(pageable.page-1, pageable.size, sort)
        }
    }
}