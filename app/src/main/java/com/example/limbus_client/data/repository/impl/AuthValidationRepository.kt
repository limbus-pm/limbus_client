// ========================================
// 4. AuthValidationRepository.kt - Validaciones
// ========================================
package com.example.limbus_client.data.repository.impl

import android.util.Patterns

class AuthValidationRepository {
    // Validación para email
    fun validateEmail(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult(false, "El correo electrónico es requerido")
            email.length > 100 -> ValidationResult(false, "El correo es demasiado largo")
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                ValidationResult(false, "Formato de correo inválido")
            else -> ValidationResult(true)
        }
    }

    // Validación para contraseña
    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isBlank() -> ValidationResult(false, "La contraseña es requerida")
            password.length < 6 -> ValidationResult(false, "La contraseña debe tener al menos 6 caracteres")
            password.length > 50 -> ValidationResult(false, "La contraseña es demasiado larga")
            !password.any { it.isDigit() } -> ValidationResult(false, "La contraseña debe contener al menos un número")
            !password.any { it.isLetter() } -> ValidationResult(false, "La contraseña debe contener al menos una letra")
            else -> ValidationResult(true)
        }
    }

    // Validación para campos de registro
    fun validateRegistrationFields(email: String, password: String): List<ValidationResult> {
        return listOf(
            validateEmail(email),
            validatePassword(password)
        )
    }

    // Validación para Google Token
    fun validateGoogleToken(token: String): ValidationResult {
        return when {
            token.isBlank() -> ValidationResult(false, "Token de Google inválido")
            token.length < 10 -> ValidationResult(false, "Token de Google demasiado corto")
            else -> ValidationResult(true)
        }
    }

    // Validación para email de recuperación
    fun validateForgotPasswordEmail(email: String): ValidationResult {
        return validateEmail(email)
    }
}

// Clase para resultado de validación
data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)
