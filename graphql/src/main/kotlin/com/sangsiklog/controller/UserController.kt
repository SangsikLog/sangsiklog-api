package com.sangsiklog.controller

import com.sangsiklog.model.user.User
import com.sangsiklog.service.user.UserService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class UserController(
    private val userService: UserService
) {

    @QueryMapping
    fun getUser(@Argument userId: Long): User? {
        return userService.getUser(userId)
    }

    @MutationMapping
    fun createUser(@Argument nickname: String, @Argument email: String, @Argument password: String): User? {
        return userService.createUser(nickname, email, password)
    }

    @MutationMapping
    fun updateUser(@Argument userId: Long, @Argument nickname: String): User? {
        return userService.updateUser(userId, nickname)
    }
}