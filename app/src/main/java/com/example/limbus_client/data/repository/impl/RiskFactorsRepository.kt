package com.example.limbus_client.data.repository.impl

import com.example.limbus_client.data.model.remote.RiskFactorsRequest
import com.example.limbus_client.data.model.remote.RiskFactorsResponse
import com.example.limbus_client.data.model.remote.RiskFactorsData
import com.example.limbus_client.data.model.remote.ValidationResult
import kotlinx.coroutines.delay
import java.util.*

class RiskFactorsRepository {

    private val validationRepository = RiskFactorsValidationRepository()

    // Simulación de almacenamiento local (en una implementación real usarías Room o SharedPreferences)
    private val localStorage = mutableMapOf<String, RiskFactorsData>()

    // Guardar factores de riesgo localmente
    suspend fun saveRiskFactorsLocally(
        userId: String,
        riskFactors: RiskFactorsRequest
    ): ValidationResult {
        try {
            // Validar los datos antes de guardar
            val validation = validationRepository.validateRiskFactors(riskFactors)
            if (!validation.isValid) {
                return validation
            }

            val riskScore = validationRepository.calculateRiskScore(riskFactors)

            val riskFactorsData = RiskFactorsData(
                userId = userId,
                hipertension = riskFactors.hipertension,
                diabetes = riskFactors.diabetes,
                colesterolElevado = riskFactors.colesterolElevado,
                antecedentesFamiliares = riskFactors.antecedentesFamiliares,
                sobrepeso = riskFactors.sobrepeso,
                sedentarismo = riskFactors.sedentarismo,
                tabaquismo = riskFactors.tabaquismo,
                estresCronico = riskFactors.estresCronico,
                riskScore = riskScore,
                fechaCreacion = riskFactors.fechaRegistro
            )

            localStorage[userId] = riskFactorsData

            return ValidationResult(true, "Factores de riesgo guardados correctamente")

        } catch (e: Exception) {
            return ValidationResult(false, "Error al guardar los factores de riesgo: ${e.message}")
        }
    }

    // Enviar factores de riesgo al servidor
    suspend fun sendRiskFactorsToServer(riskFactors: RiskFactorsRequest): RiskFactorsResponse {
        return try {
            // Simular llamada de red
            delay(1500)

            // Validar datos
            val validation = validationRepository.validateRiskFactors(riskFactors)
            if (!validation.isValid) {
                return RiskFactorsResponse(
                    success = false,
                    message = validation.errorMessage ?: "Datos inválidos"
                )
            }

            // Calcular puntuación de riesgo
            val riskScore = validationRepository.calculateRiskScore(riskFactors)

            // Generar recomendaciones
            val recommendations = validationRepository.generateRecommendations(riskFactors)

            // Simular respuesta exitosa del servidor
            RiskFactorsResponse(
                success = true,
                message = "Factores de riesgo registrados correctamente",
                riskScore = riskScore,
                recommendations = recommendations
            )

        } catch (e: Exception) {
            RiskFactorsResponse(
                success = false,
                message = "Error de conexión: ${e.message}"
            )
        }
    }

    // Obtener factores de riesgo guardados localmente
    fun getRiskFactorsLocally(userId: String): RiskFactorsData? {
        return localStorage[userId]
    }

    // Actualizar factores de riesgo existentes
    suspend fun updateRiskFactors(
        userId: String,
        riskFactors: RiskFactorsRequest
    ): ValidationResult {
        try {
            val existingData = localStorage[userId]
            if (existingData == null) {
                return ValidationResult(false, "No se encontraron datos previos para actualizar")
            }

            val validation = validationRepository.validateRiskFactors(riskFactors)
            if (!validation.isValid) {
                return validation
            }

            val riskScore = validationRepository.calculateRiskScore(riskFactors)

            val updatedData = existingData.copy(
                hipertension = riskFactors.hipertension,
                diabetes = riskFactors.diabetes,
                colesterolElevado = riskFactors.colesterolElevado,
                antecedentesFamiliares = riskFactors.antecedentesFamiliares,
                sobrepeso = riskFactors.sobrepeso,
                sedentarismo = riskFactors.sedentarismo,
                tabaquismo = riskFactors.tabaquismo,
                estresCronico = riskFactors.estresCronico,
                riskScore = riskScore,
                fechaActualizacion = System.currentTimeMillis()
            )

            localStorage[userId] = updatedData

            return ValidationResult(true, "Factores de riesgo actualizados correctamente")

        } catch (e: Exception) {
            return ValidationResult(false, "Error al actualizar: ${e.message}")
        }
    }

    // Eliminar factores de riesgo del almacenamiento local
    fun deleteRiskFactorsLocally(userId: String): ValidationResult {
        return try {
            localStorage.remove(userId)
            ValidationResult(true, "Datos eliminados correctamente")
        } catch (e: Exception) {
            ValidationResult(false, "Error al eliminar datos: ${e.message}")
        }
    }

    // Obtener estadísticas de riesgo
    fun getRiskStatistics(userId: String): Map<String, Any>? {
        val data = localStorage[userId] ?: return null

        val totalFactors = listOf(
            data.hipertension,
            data.diabetes,
            data.colesterolElevado,
            data.antecedentesFamiliares,
            data.sobrepeso,
            data.sedentarismo,
            data.tabaquismo,
            data.estresCronico
        ).count { it }

        val riskLevel = when (data.riskScore) {
            in 0..20 -> "Bajo"
            in 21..50 -> "Moderado"
            in 51..80 -> "Alto"
            else -> "Muy Alto"
        }

        return mapOf(
            "totalFactors" to totalFactors,
            "riskScore" to data.riskScore,
            "riskLevel" to riskLevel,
            "lastUpdate" to data.fechaActualizacion
        )
    }
}