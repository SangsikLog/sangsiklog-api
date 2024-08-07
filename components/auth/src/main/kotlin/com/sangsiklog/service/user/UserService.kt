package com.sangsiklog.service.user

import com.sangsiklog.controller.user.request.CreateUserRequest
import com.sangsiklog.controller.user.request.UpdateUserRequest
import com.sangsiklog.controller.user.response.CreateUserResponse
import com.sangsiklog.controller.user.response.UserDetailsResponse
import com.sangsiklog.domain.user.User
import com.sangsiklog.exception.user.UserServiceException
import com.sangsiklog.repository.user.UserRepository
import com.sangsiklog.core.api.exception.ErrorType
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun createUser(request: CreateUserRequest): CreateUserResponse {
        val user = User.create(name = request.name, email = request.email, password = request.password)
        userRepository.save(user)

        return CreateUserResponse(
            id = user.id!!
        )
    }

    fun getUserDetails(userId: Long): UserDetailsResponse {
        val user = userRepository.findById(userId)
            .orElseThrow{ UserServiceException(HttpStatus.BAD_REQUEST, ErrorType.NOT_FOUND_USER) }

        return UserDetailsResponse(
            id = user.id!!,
            name = user.name,
            profileImageUrl = user.profileImageUrl,
            email = user.email
        )
    }

    fun updateUser(userId: Long, request: UpdateUserRequest): UserDetailsResponse {
        val user = userRepository.findById(userId)
            .orElseThrow{ UserServiceException(HttpStatus.BAD_REQUEST, ErrorType.NOT_FOUND_USER) }

        user.update(request.name, request.profileImageUrl)

        return UserDetailsResponse(
            id = user.id!!,
            name = user.name,
            profileImageUrl = user.profileImageUrl,
            email = user.email
        )
    }
}