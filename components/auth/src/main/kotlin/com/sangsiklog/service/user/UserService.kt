package com.sangsiklog.service.user

import com.sangsiklog.controller.user.request.CreateUserRequest
import com.sangsiklog.controller.user.request.ChangePasswordRequest
import com.sangsiklog.controller.user.request.UpdateUserRequest
import com.sangsiklog.controller.user.response.CreateUserResponse
import com.sangsiklog.controller.user.response.UserDetailsResponse
import com.sangsiklog.domain.user.User
import com.sangsiklog.exception.user.UserServiceException
import com.sangsiklog.repository.user.UserRepository
import com.sangsiklog.core.api.exception.ErrorType
import com.sangsiklog.domain.user.UserLoginHistory
import com.sangsiklog.repository.user.UserLoginHistoryRepository
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
    private val userLoginHistoryRepository: UserLoginHistoryRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @Transactional
    fun createUser(request: CreateUserRequest): CreateUserResponse {
        val user = User.create(
            name = request.name,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            userLoginHistories = mutableListOf()
        )

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

    @Transactional
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

    @Transactional
    fun changePassword(userId: Long, request: ChangePasswordRequest) {
        val user = userRepository.findById(userId)
            .orElseThrow{ UserServiceException(HttpStatus.BAD_REQUEST, ErrorType.NOT_FOUND_USER) }

        if (passwordEncoder.matches(request.oldPassword, user.password)) {
            user.changePassword(passwordEncoder.encode(request.newPassword))
        } else {
            throw UserServiceException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_OLD_PASSWORD)
        }
    }

    fun getUserByEmail(email: String): User {
        return userRepository.findByEmail(email)
            .orElseThrow { UserServiceException(HttpStatus.BAD_REQUEST, ErrorType.NOT_FOUND_USER) }
    }

    @Transactional
    fun createUserLoginHistory(user: User, ipAddress: String) {
        val userLoginHistory = UserLoginHistory.create(
            user = user,
            loginTime = LocalDateTime.now(),
            ipAddress = ipAddress
        )

        userLoginHistoryRepository.save(userLoginHistory)
    }
}