package com.example.limbus_client.data.model.remote

import java.util.*

// DTOs para peticiones de red de registro
data class RegistrationPersonalDataRequest(
    val fechaNacimiento: String,
    val genero: String,
    val altura: Int,
    val peso: Double,
    val imc: Double
)

data class RegistrationCompleteRequest(
    val email: String,
    val password: String,
    val personalData: RegistrationPersonalDataRequest
)

// Modelos locales para el formulario
data class PersonalData(
    val fechaNacimiento: String = "",
    val genero: String = "",
    val altura: String = "",
    val peso: String = "",
    val imc: Double = 0.0,
    val imcCategoria: String = ""
)

// Resultado de validación
data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)

// Estados del formulario
data class RegistrationUiState(
    val fechaNacimiento: String = "",
    val genero: String = "",
    val altura: String = "",
    val peso: String = "",
    val imc: Double = 0.0,
    val imcCategoria: String = "",
    val generoExpanded: Boolean = false,
    val generosList: List<String> = listOf("Masculino", "Femenino", "Prefiero no decirlo"),
    val fechaNacimientoError: String? = null,
    val generoError: String? = null,
    val alturaError: String? = null,
    val pesoError: String? = null,
    val isFormValid: Boolean = false,
    val message: String? = null,
    val isLoading: Boolean = false
)

// Resultado de operaciones
sealed class RegistrationResult {
    object Success : RegistrationResult()
    data class Error(val message: String) : RegistrationResult()
    object Loading : RegistrationResult()
}