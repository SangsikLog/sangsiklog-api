package com.sangsiklog.service.user

import com.sangsiklog.core.api.exception.ErrorType
import com.sangsiklog.core.api.lock.DistributedLock
import com.sangsiklog.core.api.lock.LockType
import com.sangsiklog.domain.user.User
import com.sangsiklog.domain.user.UserLoginHistory
import com.sangsiklog.exception.user.UserServiceException
import com.sangsiklog.repository.user.UserLoginHistoryRepository
import com.sangsiklog.repository.user.UserRepository
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
    fun getUserById(userId: Long): User {
        return userRepository.findById(userId)
            .orElseThrow{ UserServiceException(HttpStatus.BAD_REQUEST, ErrorType.NOT_FOUND_USER) }
    }

    fun getUserByEmail(email: String): User {
        return userRepository.findByEmail(email)
            .orElseThrow { UserServiceException(HttpStatus.BAD_REQUEST, ErrorType.NOT_FOUND_USER) }
    }

    @Transactional
    @DistributedLock(value = LockType.CREATE_USER, keys = ["#email"])
    fun createUser(nickname: String, email: String, password: String): User {
        val existsUser = userRepository.findByEmail(email).orElse(null)
        if (existsUser != null) {
            throw UserServiceException(HttpStatus.BAD_REQUEST, ErrorType.EMAIL_ALREADY_EXISTS)
        }

        val user = User.create(
            nickname = nickname,
            email = email,
            password = passwordEncoder.encode(password)
        )

        userRepository.save(user)

        return user
    }

    @Transactional
    @DistributedLock(value = LockType.UPDATE_USER, keys = ["#userId"])
    fun updateUser(userId: Long, nickname: String, profileImageUrl: String?): User {
        val user = userRepository.findById(userId)
            .orElseThrow{ UserServiceException(HttpStatus.BAD_REQUEST, ErrorType.NOT_FOUND_USER) }

        user.update(
            nickname = nickname,
            profileImageUrl = profileImageUrl
        )

        return user
    }

    @Transactional
    @DistributedLock(value = LockType.CHANGE_PASSWORD_USER, keys = ["#userId"])
    fun changePassword(userId: Long, oldPassword: String, newPassword: String) {
        val user = userRepository.findById(userId)
            .orElseThrow{ UserServiceException(HttpStatus.BAD_REQUEST, ErrorType.NOT_FOUND_USER) }

        if (passwordEncoder.matches(oldPassword, user.password)) {
            user.changePassword(passwordEncoder.encode(newPassword))
        } else {
            throw UserServiceException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_OLD_PASSWORD)
        }
    }

    @Transactional
    fun createUserLoginHistory(user: User, ipAddress: String): UserLoginHistory {
        val userLoginHistory = UserLoginHistory.create(
            user = user,
            loginTime = LocalDateTime.now(),
            ipAddress = ipAddress
        )

        userLoginHistoryRepository.save(userLoginHistory)

        return userLoginHistory
    }
}