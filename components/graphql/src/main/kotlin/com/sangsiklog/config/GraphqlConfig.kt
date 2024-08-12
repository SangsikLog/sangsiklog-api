package com.sangsiklog.config

import com.sangsiklog.directive.AuthenticationDirective
import graphql.scalars.ExtendedScalars
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.RuntimeWiringConfigurer

@Configuration
class GraphqlConfig {

    @Bean
    fun runtimeWiringConfigurer(authenticationDirective: AuthenticationDirective, ): RuntimeWiringConfigurer {
        return RuntimeWiringConfigurer { wiring ->
            wiring.scalar(ExtendedScalars.Date)
                .scalar(ExtendedScalars.DateTime)
                .scalar(ExtendedScalars.GraphQLLong)
                .directive("authenticate", authenticationDirective)
        }
    }
}