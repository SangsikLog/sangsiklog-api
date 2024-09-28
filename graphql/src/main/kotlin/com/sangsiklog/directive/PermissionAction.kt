package com.sangsiklog.directive

import graphql.schema.DataFetchingEnvironment
import java.util.function.Consumer

class PermissionAction(
    val permission: String? = null,
    private val action: Consumer<DataFetchingEnvironment>? = null
) {
    fun executeAction(env: DataFetchingEnvironment) {
        action?.accept(env)
    }
}