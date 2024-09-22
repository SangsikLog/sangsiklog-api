package com.sangsiklog.exception.like

import com.sangsiklog.core.api.exception.ErrorType
import com.sangsiklog.core.api.exception.GrpcCustomException

class LikeServiceException(override val errorType: ErrorType): GrpcCustomException(errorType)