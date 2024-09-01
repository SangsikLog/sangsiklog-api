package com.sangsiklog.config

import com.sangsiklog.exception.StatusExceptionInterceptor
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.kotlin.AbstractCoroutineServerImpl
import io.grpc.protobuf.services.ProtoReflectionService
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GrpcServerConfig (
    private val applicationContext: ApplicationContext,
    @Value("\${grpc.server.port}") private val port: Int
) {

    private lateinit var server: Server

    @PostConstruct
    fun startGrpcServer() {
        val grpcServices = applicationContext.getBeansOfType(AbstractCoroutineServerImpl::class.java).values

        val serverBuilder = ServerBuilder.forPort(port)
        grpcServices.forEach { service ->
            serverBuilder.addService(service)
        }

        serverBuilder.addService(ProtoReflectionService.newInstance())
        serverBuilder.intercept(StatusExceptionInterceptor())

        server = serverBuilder.build().start()

        println("gRPC Server started on port ${server.port} with services: ${grpcServices.map { it.bindService().serviceDescriptor.name }}")
    }

    @PreDestroy
    fun stopGrpcServer() {
        server.shutdown()
        println("gRPC Server stopped")
    }

    @Bean
    fun grpcServer(): Server {
        return server
    }
}