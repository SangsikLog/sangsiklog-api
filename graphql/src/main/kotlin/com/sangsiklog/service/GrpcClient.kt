package com.sangsiklog.service

import com.sangsiklog.config.GrpcProperties
import com.sangsiklog.service.knowledge.KnowledgeServiceGrpcKt
import io.grpc.ManagedChannelBuilder
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.stereotype.Component

@Component
class GrpcClient(
    private val discoveryClient: DiscoveryClient,
    private val grpcProperties: GrpcProperties
) {
    private fun getServiceAddress(serviceName: String): String {
        val instances = discoveryClient.getInstances(serviceName)
        return if (instances.isNotEmpty()) {
            val instance = instances[0]
            "${instance.host}:9090"
        } else {
            throw RuntimeException("Service $serviceName not found")
        }
    }

    fun createKnowledgeServiceStub(): KnowledgeServiceGrpcKt.KnowledgeServiceCoroutineStub {
        val address = getServiceAddress(grpcProperties.contentApi.serviceName)
        val channel = ManagedChannelBuilder.forTarget(address)
            .usePlaintext()
            .build()
        return KnowledgeServiceGrpcKt.KnowledgeServiceCoroutineStub(channel)
    }
}