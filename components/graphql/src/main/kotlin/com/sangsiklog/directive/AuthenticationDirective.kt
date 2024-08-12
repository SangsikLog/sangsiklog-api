package com.sangsiklog.directive

import com.sangsiklog.exception.UnauthorizedException
import graphql.schema.DataFetcher
import graphql.schema.GraphQLFieldDefinition
import graphql.schema.GraphQLObjectType
import graphql.schema.idl.SchemaDirectiveWiring
import graphql.schema.idl.SchemaDirectiveWiringEnvironment
import org.springframework.stereotype.Component

@Component
class AuthenticationDirective: SchemaDirectiveWiring {
    override fun onField(environment: SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition>?): GraphQLFieldDefinition {
        val fieldDefinition = environment?.fieldDefinition
        val parentType = environment?.fieldsContainer as GraphQLObjectType

        val originalDataFetcher = environment.codeRegistry.getDataFetcher(parentType, fieldDefinition)

        val authDataFetcher = DataFetcher { dataFetchingEnvironment ->
            val userId = dataFetchingEnvironment.graphQlContext.get<String>("X-USER-ID")
            if (userId == null || userId.trim().isEmpty() || userId.equals("-1")) {
                throw UnauthorizedException("Unauthorized: Missing X-USER-ID header")
            }

           originalDataFetcher.get(dataFetchingEnvironment)
        }

        environment.codeRegistry.dataFetcher(parentType, fieldDefinition, authDataFetcher)

        return fieldDefinition?:throw RuntimeException("Authentication directive error")
    }
}