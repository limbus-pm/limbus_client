package com.example.limbus_client.data.model.remote

import java.util.*

// DTOs para peticiones de red de factores de riesgo
data class RiskFactorsRequest(
    val hipertension: Boolean,
    val diabetes: Boolean,
    val colesterolElevado: Boolean,
    val antecedentesFamiliares: Boolean,
    val sobrepeso: Boolean,
    val sedentarismo: Boolean,
    val tabaquismo: Boolean,
    val estresCronico: Boolean,
    val fechaRegistro: Long = System.currentTimeMillis()
)

// DTO para respuesta del servidor
data class RiskFactorsResponse(
    val success: Boolean,
    val message: String,
    val riskScore: Int? = null, // Puntuación de riesgo calculada
    val recommendations: List<String>? = null
)

// Nota: ValidationResult ya existe en RegistrationDataModels.kt
// Si necesitas importarla, usa: import com.example.limbus_client.data.model.remote.ValidationResult

// Datos completos de factores de riesgo para persistencia local
data class RiskFactorsData(
    val id: String = UUID.randomUUID().toString(),
    val userId: String,
    val hipertension: Boolean,
    val diabetes: Boolean,
    val colesterolElevado: Boolean,
    val antecedentesFamiliares: Boolean,
    val sobrepeso: Boolean,
    val sedentarismo: Boolean,
    val tabaquismo: Boolean,
    val estresCronico: Boolean,
    val riskScore: Int = 0,
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)