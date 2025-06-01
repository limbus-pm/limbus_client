// ========================================
// 6. AuthDataModels.kt - Modelos de datos
// ========================================
package com.example.limbus_client.data.model.remote

// DTOs para peticiones de red
data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val email: String,
    val password: String
)

data class ForgotPasswordRequest(
    val email: String
)

data class GoogleLoginRequest(
    val googleToken: String
)

// DTOs para respuestas de red
data class LoginResponse(
    val accessToken: String,
    val user: User
)

data class RegisterResponse(
    val message: String,
    val user: User
)

data class ForgotPasswordResponse(
    val message: String
)

data class User(
    val id: Long,
    val email: String,
    val firstName: String,
    val lastName: String
)

// Entidades para BD local
data class UserEntity(
    val id: Long,
    val email: String,
    val firstName: String,
    val lastName: String,
    val isLoggedIn: Boolean
)

data class AuthTokenEntity(
    val userId: Long,
    val accessToken: String,
    val expiresAt: Long
)