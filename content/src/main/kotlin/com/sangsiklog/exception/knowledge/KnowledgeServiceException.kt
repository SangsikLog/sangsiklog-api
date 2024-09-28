package com.sangsiklog.exception.knowledge

import com.sangsiklog.core.api.exception.ErrorType
import com.sangsiklog.core.api.exception.GrpcCustomException

class KnowledgeServiceException(override val errorType: ErrorType): GrpcCustomException(errorType)