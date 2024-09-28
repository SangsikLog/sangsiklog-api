package com.sangsiklog.core.grpc

import io.grpc.Attributes
import io.grpc.EquivalentAddressGroup
import io.grpc.NameResolver
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import java.net.InetSocketAddress

class EurekaNameResolver(
    private val discoveryClient: DiscoveryClient,
    private val serviceName: String,
    private val port: String
) : NameResolver(), ApplicationListener<ContextRefreshedEvent> {

    private var listener: Listener2? = null

    override fun start(listener: Listener2?) {
        this.listener = listener
        refreshServiceInstances()
    }

    override fun refresh() {
        refreshServiceInstances()
    }

    private fun refreshServiceInstances() {
        val instances = discoveryClient.getInstances(serviceName)
        val addressGroups = instances.map { instance ->
            EquivalentAddressGroup(InetSocketAddress(instance.host, port.toInt()))
        }

        listener?.onResult(ResolutionResult.newBuilder()
            .setAddresses(addressGroups)
            .setAttributes(Attributes.EMPTY)
            .build())
    }

    override fun shutdown() {
    }

    override fun getServiceAuthority(): String {
        return serviceName
    }

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        refresh()
    }

    @EventListener
    fun onInstanceRegistered(event: InstanceRegisteredEvent<*>) {
        refresh()
    }
}