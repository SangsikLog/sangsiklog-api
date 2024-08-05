package com.sangsiklog.auth.controller.user

import com.sangsiklog.auth.controller.user.request.CreateUserRequest
import com.sangsiklog.auth.controller.user.response.CreateUserResponse
import com.sangsiklog.auth.service.user.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @PostMapping
    fun creatUser(@RequestBody request: CreateUserRequest): CreateUserResponse {
        return userService.createUser(request)
    }


}