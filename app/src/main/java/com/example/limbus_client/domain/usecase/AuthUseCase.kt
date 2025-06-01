// ========================================
// 5. AuthUseCase.kt - Casos de uso
// ========================================
package com.example.limbus_client.domain.usecase

import com.example.limbus_client.data.repository.impl.AuthValidationRepository

class AuthUseCase(
    private val validationRepository: AuthValidationRepository
) {
    // Lógica para el botón "Iniciar sesión"
    suspend fun executeLogin(email: String, password: String): AuthResult {
        // Limpiar espacios en blanco
        val cleanEmail = email.trim()
        val cleanPassword = password.trim()

        // Validar email
        val emailValidation = validationRepository.validateEmail(cleanEmail)
        if (!emailValidation.isValid) {
            return AuthResult.Error(emailValidation.errorMessage ?: "Email inválido")
        }

        // Validar contraseña
        val passwordValidation = validationRepository.validatePassword(cleanPassword)
        if (!passwordValidation.isValid) {
            return AuthResult.Error(passwordValidation.errorMessage ?: "Contraseña inválida")
        }

        // Aquí iría la lógica de llamada al API
        return try {
            // Simular llamada de red con delay
            kotlinx.coroutines.delay(2000) // 2 segundos de delay para simular red

            // Simular diferentes respuestas basadas en el email
            when {
                cleanEmail.contains("test@") -> AuthResult.Success("Inicio de sesión exitoso")
                cleanEmail.contains("error@") -> AuthResult.Error("Credenciales incorrectas")
                else -> AuthResult.Success("Bienvenido a Limbus")
            }
        } catch (e: Exception) {
            AuthResult.Error("Error de conexión: ${e.message}")
        }
    }

    // Lógica para el botón "Regístrese"
    suspend fun executeRegister(email: String, password: String): AuthResult {
        val cleanEmail = email.trim()
        val cleanPassword = password.trim()

        // Validar todos los campos
        val validations = validationRepository.validateRegistrationFields(cleanEmail, cleanPassword)
        val firstError = validations.firstOrNull { !it.isValid }

        if (firstError != null) {
            return AuthResult.Error(firstError.errorMessage ?: "Datos inválidos")
        }

        return try {
            // Simular llamada de red
            kotlinx.coroutines.delay(1500) // 1.5 segundos de delay
            AuthResult.Success("Registro exitoso. Proceda con el formulario.")
        } catch (e: Exception) {
            AuthResult.Error("Error en el registro: ${e.message}")
        }
    }

    // Lógica para "¿Olvidó su contraseña?"
    suspend fun executeForgotPassword(email: String): AuthResult {
        val cleanEmail = email.trim()
        val emailValidation = validationRepository.validateForgotPasswordEmail(cleanEmail)

        if (!emailValidation.isValid) {
            return AuthResult.Error(emailValidation.errorMessage ?: "Email inválido")
        }

        return try {
            // Simular llamada de red
            kotlinx.coroutines.delay(1000) // 1 segundo de delay
            AuthResult.Success("Instrucciones enviadas a su correo: $cleanEmail")
        } catch (e: Exception) {
            AuthResult.Error("Error al enviar instrucciones: ${e.message}")
        }
    }

    // Lógica para "Continuar con Google"
    suspend fun executeGoogleLogin(googleToken: String): AuthResult {
        val tokenValidation = validationRepository.validateGoogleToken(googleToken)

        if (!tokenValidation.isValid) {
            return AuthResult.Error(tokenValidation.errorMessage ?: "Error con Google")
        }

        return try {
            // Simular llamada de red
            kotlinx.coroutines.delay(1500) // 1.5 segundos de delay
            AuthResult.Success("Inicio de sesión con Google exitoso")
        } catch (e: Exception) {
            AuthResult.Error("Error con Google: ${e.message}")
        }
    }
}

// Clase para manejar resultados de autenticación
sealed class AuthResult {
    object Loading : AuthResult()
    data class Success(val message: String) : AuthResult()
    data class Error(val message: String) : AuthResult()
}
