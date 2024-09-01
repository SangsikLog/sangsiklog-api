package com.sangsiklog.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.sangsiklog.core.api.exception.ErrorType
import com.sangsiklog.core.api.exception.GrpcCustomException
import io.grpc.*

class StatusExceptionInterceptor: ServerInterceptor {
    private val objectMapper = ObjectMapper()

    override fun <ReqT : Any?, RespT : Any?> interceptCall(
        call: ServerCall<ReqT, RespT>,
        headers: Metadata,
        next: ServerCallHandler<ReqT, RespT>
    ): ServerCall.Listener<ReqT> {
        val serverCall =
            object : ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
                override fun close(status: Status, trailers: Metadata) {
                    if (status.isOk || status.cause == null) {
                        super.close(status, trailers)
                    } else {
                        val errorStatus: ErrorStatus
                        val newStatus: Status

                        when (val throwable = status.cause) {
                            is GrpcCustomException -> {
                                errorStatus = ErrorStatus(throwable.errorType, throwable.toString())
                                newStatus = Status.INTERNAL.withDescription(
                                    objectMapper.writeValueAsString(errorStatus)
                                )
                            }
                            else -> {
                                errorStatus = ErrorStatus(ErrorType.UNKNOWN, throwable.toString())
                                newStatus = Status.UNKNOWN.withDescription(
                                    objectMapper.writeValueAsString(errorStatus)
                                )
                            }
                        }

                        super.close(newStatus, trailers)
                    }
                }
            }

        return next.startCall(serverCall, headers)
    }
}