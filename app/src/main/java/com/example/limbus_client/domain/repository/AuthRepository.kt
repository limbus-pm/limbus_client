package com.example.limbus_client.domain.repository

import com.example.limbus_client.domain.model.LoginRequest
import com.example.limbus_client.domain.model.LoginResponse
import com.example.limbus_client.domain.model.User

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse>
    suspend fun logout(): Result<Unit>
    suspend fun getCurrentUser(): User?
    suspend fun saveUserSession(token: String, user: User)
    suspend fun clearUserSession()
    suspend fun isUserLoggedIn(): Boolean
    suspend fun getStoredToken(): String?
}