package com.sangsiklog.controller.user

import com.sangsiklog.controller.user.request.CreateUserRequest
import com.sangsiklog.controller.user.request.ChangePasswordRequest
import com.sangsiklog.controller.user.request.UpdateUserRequest
import com.sangsiklog.controller.user.response.CreateUserResponse
import com.sangsiklog.controller.user.response.UserDetailsResponse
import com.sangsiklog.service.auth.AuthService
import com.sangsiklog.service.user.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val authService: AuthService,
    private val userService: UserService
) {
    @PostMapping
    fun creatUser(@RequestBody request: CreateUserRequest): CreateUserResponse {
        authService.isEmailVerified(request.email)

        val user = userService.createUser(
            nickname = request.nickname,
            email = request.email,
            password = request.password
        )

        return CreateUserResponse(
            id = user.id!!
        )
    }

    @GetMapping("/{userId}")
    fun getUserDetails(@PathVariable userId: Long): UserDetailsResponse {
        val user = userService.getUserById(userId)

        return UserDetailsResponse(
            id = user.id!!,
            nickname = user.nickname,
            profileImageUrl = user.profileImageUrl,
            email = user.email
        )
    }

    @PatchMapping("/{userId}")
    fun updateUser(@PathVariable userId: Long, @RequestBody request: UpdateUserRequest): UserDetailsResponse {
        val user = userService.updateUser(
            userId = userId,
            nickname = request.nickname,
            profileImageUrl = request.profileImageUrl
        )

        return UserDetailsResponse(
            id = user.id!!,
            nickname = user.nickname,
            profileImageUrl = user.profileImageUrl,
            email = user.email
        )
    }

    @PostMapping("/{userId}/password-change")
    fun changePassword(@PathVariable userId: Long, @RequestBody request: ChangePasswordRequest): String {
        userService.changePassword(
            userId = userId,
            oldPassword = request.oldPassword,
            newPassword = request.newPassword
        )
        return "ok"
    }
}