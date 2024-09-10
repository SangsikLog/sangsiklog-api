package com.sangsiklog.domain.user

import com.sangsiklog.domain.base.BaseEntity
import jakarta.persistence.*

@Entity
@SequenceGenerator(
    name = "USER_SEQ_GENERATOR",
    sequenceName = "USER_SEQ",
    initialValue = 1,
    allocationSize = 50
)
@Table(name = "users")
class User(
    nickname: String,
    email: String,
    password: String
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
    @Column(name = "user_id")
    val id: Long? = null

    @Column(name = "nickname", nullable = false)
    var nickname: String = nickname
        protected set

    @Column(name = "email", unique = true, nullable = false)
    val email: String = email

    @Column(name = "profile_image_url")
    var profileImageUrl: String? = null

    @Column(name = "password", nullable = false)
    var password: String = password
        protected set

    @OneToMany(mappedBy = "user", cascade = [(CascadeType.ALL)])
    var userLoginHistories: MutableList<UserLoginHistory> = mutableListOf()

    companion object {
        fun create(nickname: String, email: String, password: String): User {
            return User(
                nickname = nickname,
                email = email,
                password = password
            )
        }
    }

    fun update(nickname: String, profileImageUrl: String?) {
        this.nickname = nickname
        this.profileImageUrl = profileImageUrl
    }

    fun changePassword(newPassword: String) {
        this.password = newPassword
    }
}