package com.sangsiklog.repository.user

import com.sangsiklog.domain.user.UserLoginHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserLoginHistoryRepository : JpaRepository<UserLoginHistory, Long> {
}