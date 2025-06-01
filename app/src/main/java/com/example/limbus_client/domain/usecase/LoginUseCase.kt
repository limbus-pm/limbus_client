package com.example.limbus_client.domain.usecase

import com.example.limbus_client.domain.model.LoginRequest
import com.example.limbus_client.domain.model.LoginResponse
import com.example.limbus_client.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<LoginResponse> {
        // Validaciones
        if (email.isBlank()) {
            return Result.failure(Exception("El correo electrónico es requerido"))
        }

        if (!isValidEmail(email)) {
            return Result.failure(Exception("El formato del correo electrónico no es válido"))
        }

        if (password.isBlank()) {
            return Result.failure(Exception("La contraseña es requerida"))
        }

        if (password.length < 6) {
            return Result.failure(Exception("La contraseña debe tener al menos 6 caracteres"))
        }

        val loginRequest = LoginRequest(email.trim(), password)

        return try {
            val result = authRepository.login(loginRequest)
            if (result.isSuccess) {
                val loginResponse = result.getOrThrow()
                // Guardar sesión del usuario
                authRepository.saveUserSession(loginResponse.token, loginResponse.user)
            }
            result
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}