// ========================================
// 5. RegistrationDataModels.kt - Modelos específicos del registro
// ========================================
package com.example.limbus_client.data.model.remote

// DTOs para peticiones de registro
data class RegisterStep1Request(
    val nombre: String,
    val apellido: String,
    val correo: String,
    val contrasena: String
)

data class CheckEmailAvailabilityRequest(
    val correo: String
)

// DTOs para respuestas de registro
data class RegisterStep1Response(
    val message: String,
    val tempUserId: String,
    val nextStep: String
)

data class EmailAvailabilityResponse(
    val available: Boolean,
    val message: String
)

// Entidades para BD local
data class RegistrationTempEntity(
    val tempId: String,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val contrasenaHash: String,
    val step: Int,
    val createdAt: Long,
    val expiresAt: Long
)

data class RegistrationStepEntity(
    val tempUserId: String,
    val stepNumber: Int,
    val stepData: String, // JSON con datos del step
    val completedAt: Long,
    val isValid: Boolean
)