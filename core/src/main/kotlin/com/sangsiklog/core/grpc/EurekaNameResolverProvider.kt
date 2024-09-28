package com.sangsiklog.core.grpc

import io.grpc.NameResolver
import io.grpc.NameResolverProvider
import org.springframework.cloud.client.discovery.DiscoveryClient
import java.net.URI

class EurekaNameResolverProvider(
    private val discoveryClient: DiscoveryClient,
    private val port: String
) : NameResolverProvider() {

    override fun newNameResolver(targetUri: URI, args: NameResolver.Args?): NameResolver {
        val path = targetUri.path
        if (path == null || path.isEmpty()) {
            throw IllegalArgumentException("URI path cannot be null or empty. URI: $targetUri")
        }

        val serviceName = path.removePrefix("/")
        return EurekaNameResolver(discoveryClient, serviceName, port)
    }

    override fun getDefaultScheme(): String {
        return "eureka"
    }

    override fun isAvailable(): Boolean {
        return true
    }

    override fun priority(): Int {
        return 5
    }
}