package com.sangsiklog.domain.user

import com.sangsiklog.core.domain.base.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@SequenceGenerator(
    name = "USER_LOGIN_HISTORY_SEQ_GENERATOR",
    sequenceName = "USER_LOGIN_HISTORY_SEQ",
    initialValue = 1,
    allocationSize = 50
)
@Table(name = "user_login_histories")
class UserLoginHistory(
    user: User,
    loginTime: LocalDateTime,
    ipAddress: String,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_LOGIN_HISTORY_SEQ_GENERATOR")
    @Column(name = "user_login_history_id")
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User = user

    @Column(nullable = false)
    val loginTime: LocalDateTime = loginTime

    @Column(nullable = false)
    val ipAddress: String = ipAddress

    companion object {
        fun create(user: User, loginTime: LocalDateTime, ipAddress: String): UserLoginHistory {
            val userLoginHistory = UserLoginHistory(
                user = user,
                loginTime = loginTime,
                ipAddress = ipAddress
            )
            user.userLoginHistories.add(userLoginHistory)
            return userLoginHistory
        }
    }
}