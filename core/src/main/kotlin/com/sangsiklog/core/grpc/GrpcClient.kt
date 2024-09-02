package com.sangsiklog.core.grpc

import io.grpc.CallOptions
import io.grpc.Channel
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.AbstractStub
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class GrpcClient(
    private val discoveryClient: DiscoveryClient
) {
    private fun getServiceAddress(serviceName: String, port: String): String {
        val instances = discoveryClient.getInstances(serviceName)
        return if (instances.isNotEmpty()) {
            val instance = instances[0]
            "${instance.host}:${port}"
        } else {
            throw RuntimeException("Service $serviceName not found")
        }
    }

    fun <T : AbstractStub<T>> createStub(stubClass: KClass<T>, serviceName: String, port: String): T {
        val address = getServiceAddress(serviceName, port)
        val channel: ManagedChannel = ManagedChannelBuilder.forTarget(address)
            .usePlaintext()
            .build()

        val constructor = stubClass.constructors.firstOrNull { ctor ->
            val params = ctor.parameters
            params.size == 2 && params[0].type.classifier == Channel::class && params[1].type.classifier == CallOptions::class
        } ?: throw IllegalArgumentException("Expected constructor with ManagedChannel and CallOptions not found for ${stubClass.simpleName}")

        return constructor.call(channel, CallOptions.DEFAULT)
    }
}