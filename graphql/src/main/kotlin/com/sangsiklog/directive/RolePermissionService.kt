package com.sangsiklog.directive

import com.sangsiklog.exception.UnauthorizedException
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class RolePermissionService(
    private val rolePermissions: MutableMap<String, Set<PermissionAction>> = hashMapOf()
) {
    init {
        initializeRoles()
    }

    private final fun initializeRoles() {
        val userPermissions = hashSetOf<PermissionAction>()
        userPermissions.add(PermissionAction("yourself") { env -> loginUserCheckAction(env) })

        rolePermissions["user"] = userPermissions
    }

    fun checkPermission(role: String?, permission: String?, evn: DataFetchingEnvironment): Boolean {
        val actions = rolePermissions[role]
        actions?.forEach { action ->
            if (action.permission != null && action.permission == permission) {
                action.executeAction(evn)
                return true
            }
        }

        return false
    }

    private fun loginUserCheckAction(env: DataFetchingEnvironment) {
        val headerUserId = env.graphQlContext.get<String>("X-USER-ID").toLong()
        val argumentUserId = env.getArgument<Long>("userId")

        if (headerUserId != argumentUserId) {
            throw UnauthorizedException("Unauthorized")
        }
    }
}