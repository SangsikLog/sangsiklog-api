package com.sangsiklog.domain.user

import com.sangsiklog.core.domain.base.BaseEntity
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
    name: String,
    email: String,
    password: String,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
    @Column(name = "user_id")
    val id: Long? = null

    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "email", nullable = false)
    val email: String = email

    @Column(name = "profile_image_url")
    var profileImageUrl: String? = null

    @Column(name = "password", nullable = false)
    var password: String = password
        protected set

    companion object {
        fun create(name: String, email: String, password: String): User {
            return User(name = name, email = email, password = password)
        }
    }

    fun update(name: String, profileImageUrl: String?) {
        this.name = name
        this.profileImageUrl = profileImageUrl
    }

    fun changePassword(newPassword: String) {
        this.password = newPassword
    }
}