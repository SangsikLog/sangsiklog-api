package com.sangsiklog.service.user

import com.sangsiklog.core.http.HttpClient
import com.sangsiklog.model.user.User
import com.sangsiklog.service.user.request.CreateUserRequest
import com.sangsiklog.service.user.request.UpdateUserRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class UserService(
    private val httpClient: HttpClient,
) {
    @Value("\${http.user-service.base-url}")
    lateinit var userServiceBaseUrl: String

    @Cacheable(key = "#userId", value = ["user"])
    fun getUser(userId: Long): User? {
        return httpClient.get(
            url = "${userServiceBaseUrl}/${userId}",
            responseType = User::class.java
        )
    }

    fun createUser(nickname: String, email: String, password: String): User? {
        val createUserRequest = CreateUserRequest(nickname = nickname, email = email, password = password)

        return httpClient.post(
            url = userServiceBaseUrl,
            request = createUserRequest,
            responseType = User::class.java
        )
    }

    fun updateUser(userId: Long, nickname: String): User? {
        val updateUserRequest = UpdateUserRequest(nickname = nickname)

        return httpClient.patch(
            url = "${userServiceBaseUrl}/${userId}",
            request = updateUserRequest,
            responseType = User::class.java
        )
    }
}