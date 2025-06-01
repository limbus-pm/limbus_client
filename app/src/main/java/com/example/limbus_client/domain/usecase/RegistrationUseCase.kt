// ========================================
// 4. RegistrationUseCase.kt - Casos de uso del registro
// ========================================
package com.example.limbus_client.domain.usecase

import com.example.limbus_client.data.repository.impl.RegistrationValidationRepository

class RegistrationUseCase(
    private val validationRepository: RegistrationValidationRepository
) {

    // Ejecutar Step 1 del registro
    suspend fun executeStep1(
        nombre: String,
        apellido: String,
        correo: String,
        contrasena: String,
        confirmarContrasena: String
    ): RegistrationResult {

        // Limpiar espacios en blanco
        val cleanNombre = nombre.trim()
        val cleanApellido = apellido.trim()
        val cleanCorreo = correo.trim().lowercase()
        val cleanContrasena = contrasena.trim()
        val cleanConfirmarContrasena = confirmarContrasena.trim()

        // Validar todos los campos
        val validations = validationRepository.validateCompleteStep1(
            cleanNombre,
            cleanApellido,
            cleanCorreo,
            cleanContrasena,
            cleanConfirmarContrasena
        )

        // Verificar si hay errores de validación
        val firstError = validations.firstOrNull { !it.isValid }
        if (firstError != null) {
            return RegistrationResult.Error(firstError.errorMessage ?: "Datos inválidos")
        }

        // Validar unicidad del correo
        val emailUniquenessValidation = validationRepository.validateEmailUniqueness(cleanCorreo)
        if (!emailUniquenessValidation.isValid) {
            return RegistrationResult.Error(emailUniquenessValidation.errorMessage ?: "Correo no disponible")
        }

        return try {
            // Simular llamada de red para verificar datos
            kotlinx.coroutines.delay(2000) // 2 segundos de delay

            // Simular diferentes respuestas basadas en el correo
            when {
                cleanCorreo.contains("error") ->
                    RegistrationResult.Error("Error en el servidor. Intente nuevamente.")
                cleanCorreo.contains("timeout") ->
                    RegistrationResult.Error("Tiempo de espera agotado. Verifique su conexión.")
                else -> RegistrationResult.Success("Datos válidos. Continúe con el siguiente paso.")
            }
        } catch (e: Exception) {
            RegistrationResult.Error("Error de conexión: ${e.message}")
        }
    }

    // Verificar disponibilidad de correo
    suspend fun checkEmailAvailability(correo: String): RegistrationResult {
        val cleanCorreo = correo.trim().lowercase()
        val emailValidation = validationRepository.validateCorreo(cleanCorreo)

        if (!emailValidation.isValid) {
            return RegistrationResult.Error(emailValidation.errorMessage ?: "Correo inválido")
        }

        return try {
            kotlinx.coroutines.delay(1000) // 1 segundo de delay

            val uniquenessValidation = validationRepository.validateEmailUniqueness(cleanCorreo)
            if (uniquenessValidation.isValid) {
                RegistrationResult.Success("Correo disponible")
            } else {
                RegistrationResult.Error(uniquenessValidation.errorMessage ?: "Correo no disponible")
            }
        } catch (e: Exception) {
            RegistrationResult.Error("Error al verificar correo: ${e.message}")
        }
    }
}

// Clase para manejar resultados del registro
sealed class RegistrationResult {
    object Loading : RegistrationResult()
    data class Success(val message: String) : RegistrationResult()
    data class Error(val message: String) : RegistrationResult()
}
