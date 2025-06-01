package com.example.limbus_client.domain.model

data class User(
    val id: String,
    val email: String,
    val name: String? = null,
    val isEmailVerified: Boolean = false,
    val profileImageUrl: String? = null,
    val createdAt: String? = null
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val user: User,
    val token: String,
    val refreshToken: String? = null
)