package com.sangsiklog.controller.user

import com.sangsiklog.controller.user.request.CreateUserRequest
import com.sangsiklog.controller.user.request.UpdateUserRequest
import com.sangsiklog.controller.user.response.CreateUserResponse
import com.sangsiklog.controller.user.response.UserDetailsResponse
import com.sangsiklog.service.user.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val userService: UserService
) {
    @PostMapping
    fun creatUser(@RequestBody request: CreateUserRequest): CreateUserResponse {
        return userService.createUser(request)
    }

    @GetMapping("/{userId}")
    fun getUserDetails(@PathVariable userId: Long): UserDetailsResponse {
        return userService.getUserDetails(userId)
    }

    @PutMapping("/{userId}")
    fun updateUser(@PathVariable userId: Long, @RequestBody request: UpdateUserRequest): UserDetailsResponse {
        return userService.updateUser(userId, request)
    }
}