package com.sangsiklog.config

import com.sangsiklog.directive.AuthenticationDirective
import com.sangsiklog.directive.AuthorizationDirective
import graphql.scalars.ExtendedScalars
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.RuntimeWiringConfigurer

@Configuration
class GraphqlConfig {

    @Bean
    fun runtimeWiringConfigurer(authenticationDirective: AuthenticationDirective, authorizationDirective: AuthorizationDirective): RuntimeWiringConfigurer {
        return RuntimeWiringConfigurer { wiring ->
            wiring.scalar(ExtendedScalars.Date)
                .scalar(ExtendedScalars.DateTime)
                .scalar(ExtendedScalars.GraphQLLong)
                .directive("authenticate", authenticationDirective)
                .directive("authorize", authorizationDirective)
        }
    }
}