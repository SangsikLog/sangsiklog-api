package com.sangsiklog.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "grpc.client")
class GrpcProperties {
    lateinit var contentApi: ServiceProperties

    class ServiceProperties {
        lateinit var serviceName: String
        lateinit var port: String
    }
}