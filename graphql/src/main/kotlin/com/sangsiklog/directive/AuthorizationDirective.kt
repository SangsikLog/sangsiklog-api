package com.sangsiklog.directive

import graphql.schema.DataFetcher
import graphql.schema.GraphQLFieldDefinition
import graphql.schema.GraphQLObjectType
import graphql.schema.idl.SchemaDirectiveWiring
import graphql.schema.idl.SchemaDirectiveWiringEnvironment
import org.springframework.stereotype.Component

@Component
class AuthorizationDirective(
    private val rolePermissionService: RolePermissionService
): SchemaDirectiveWiring {
    override fun onField(environment: SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition>?): GraphQLFieldDefinition {
        val requiredRole = environment?.getAppliedDirective("authorize")?.getArgument("role")?.getValue<String>()
        val requiredPermission = environment?.getAppliedDirective("authorize")?.getArgument("permission")?.getValue<String>()

        val fieldDefinition = environment?.fieldDefinition
        val parentType = environment?.fieldsContainer as GraphQLObjectType

        val originalDataFetcher = environment.codeRegistry.getDataFetcher(parentType, fieldDefinition)

        val authDataFetcher = DataFetcher { dataFetchingEnvironment ->
            if (!rolePermissionService.checkPermission(requiredRole, requiredPermission, dataFetchingEnvironment)) {
                throw RuntimeException("Do not hava permission to perform this action")
            }

            originalDataFetcher.get(dataFetchingEnvironment)
        }

        environment.codeRegistry.dataFetcher(parentType, fieldDefinition, authDataFetcher)

        return fieldDefinition?:throw RuntimeException("Authentication directive error")
    }
}