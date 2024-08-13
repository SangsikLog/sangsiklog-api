package com.sangsiklog.service.user

import com.sangsiklog.model.user.User
import com.sangsiklog.service.HttpClient
import com.sangsiklog.service.user.request.CreateUserRequest
import com.sangsiklog.service.user.request.UpdateUserRequest
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
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

    fun createUser(name: String, email: String, password: String): User? {
        val createUserRequest = CreateUserRequest(name = name, email = email, password = password)

        return httpClient.post(
            url = userServiceBaseUrl,
            request = createUserRequest,
            responseType = User::class.java
        )
    }

    fun updateUser(userId: Long, name: String): User? {
        val updateUserRequest = UpdateUserRequest(name = name)

        httpClient.put(
            url = "${userServiceBaseUrl}/${userId}",
            request = updateUserRequest
        )

        return User(
            id = userId,
            name = name,
            email = ""
        )
    }
}