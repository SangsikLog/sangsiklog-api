package com.sangsiklog.core.messaging

import com.sangsiklog.core.log.logger
import org.reflections.Reflections
import org.reflections.scanners.Scanners
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.ServiceActivator


@Configuration
class DomainEventMessageConfig {
    @Value("messaging.domain-event.base-package.listener")
    lateinit var domainEventListenerBasePackages: Set<String>

    @Bean
    fun domainEventClassNameOnListener(): Set<String> {
        return domainEventListenerBasePackages.flatMap { domainEventListenerBasePackage ->
            Reflections(domainEventListenerBasePackage, Scanners.MethodsAnnotated)
                .getMethodsAnnotatedWith(ServiceActivator::class.java)
                .map { method ->
                    val parameterType = method.parameterTypes[0].simpleName
                    val inputChannel = method.getAnnotation(ServiceActivator::class.java).inputChannel
                    if (parameterType != inputChannel) {
                        logger.error("[EventHandler] ${method.name}, [real parameter] $parameterType, [inputchannel] $inputChannel")
                        throw EventHandlerParameterMismatchException()
                    }
                    inputChannel
                }
        }.toSet()
    }
}