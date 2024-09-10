package com.sangsiklog.core.grpc

import io.grpc.*
import io.grpc.stub.AbstractStub
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class GrpcClient(
    private val discoveryClient: DiscoveryClient
) {
    fun createChannel(serviceName: String, port: String): ManagedChannel {
        NameResolverRegistry.getDefaultRegistry().register(EurekaNameResolverProvider(discoveryClient, port))

        return ManagedChannelBuilder.forTarget("eureka:///$serviceName")
            .defaultLoadBalancingPolicy("round_robin")
            .usePlaintext()
            .build()
    }

    fun <T : AbstractStub<T>> createStub(stubClass: KClass<T>, serviceName: String, port: String): T {
        val channel = createChannel(serviceName, port)

        val constructor = stubClass.constructors.firstOrNull { ctor ->
            val params = ctor.parameters
            params.size == 2 && params[0].type.classifier == Channel::class && params[1].type.classifier == CallOptions::class
        } ?: throw IllegalArgumentException("Expected constructor with ManagedChannel and CallOptions not found for ${stubClass.simpleName}")

        return constructor.call(channel, CallOptions.DEFAULT)
    }
}