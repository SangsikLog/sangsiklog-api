package com.sangsiklog.auth.service.user

import com.sangsiklog.auth.controller.user.request.CreateUserRequest
import com.sangsiklog.auth.controller.user.response.CreateUserResponse
import com.sangsiklog.auth.domain.user.User
import com.sangsiklog.auth.repository.user.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun createUser(request: CreateUserRequest) : CreateUserResponse {
        val user = User.create(name = request.name, email = request.email, password = request.password)
        userRepository.save(user)

        return CreateUserResponse(id = user.id!!)
    }
}