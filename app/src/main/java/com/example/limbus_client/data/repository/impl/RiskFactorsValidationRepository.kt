package com.example.limbus_client.data.repository.impl

import com.example.limbus_client.data.model.remote.ValidationResult
import com.example.limbus_client.data.model.remote.RiskFactorsRequest
import java.util.*

class RiskFactorsValidationRepository {

    // Validación general de factores de riesgo
    fun validateRiskFactors(riskFactors: RiskFactorsRequest): ValidationResult {
        // Verificar que al menos se haya seleccionado algún campo (aunque sea falso)
        val hasValidData = riskFactors.fechaRegistro > 0

        if (!hasValidData) {
            return ValidationResult(false, "Error en los datos de factores de riesgo")
        }

        return ValidationResult(true)
    }

    // Validación de selección mínima (opcional - puede ser útil si requieren al menos uno)
    fun validateMinimumSelection(riskFactors: RiskFactorsRequest, requireMinimum: Boolean = false): ValidationResult {
        if (!requireMinimum) {
            return ValidationResult(true)
        }

        val hasAnySelection = listOf(
            riskFactors.hipertension,
            riskFactors.diabetes,
            riskFactors.colesterolElevado,
            riskFactors.antecedentesFamiliares,
            riskFactors.sobrepeso,
            riskFactors.sedentarismo,
            riskFactors.tabaquismo,
            riskFactors.estresCronico
        ).any { it }

        return if (hasAnySelection) {
            ValidationResult(true)
        } else {
            ValidationResult(false, "Debe seleccionar al menos un factor de riesgo")
        }
    }

    // Calcular puntuación de riesgo basada en los factores seleccionados
    fun calculateRiskScore(riskFactors: RiskFactorsRequest): Int {
        var score = 0

        // Asignar puntos por cada factor de riesgo
        val riskWeights = mapOf(
            riskFactors.hipertension to 15,
            riskFactors.diabetes to 20,
            riskFactors.colesterolElevado to 10,
            riskFactors.antecedentesFamiliares to 12,
            riskFactors.sobrepeso to 8,
            riskFactors.sedentarismo to 6,
            riskFactors.tabaquismo to 18,
            riskFactors.estresCronico to 5
        )

        riskWeights.forEach { (hasRisk, weight) ->
            if (hasRisk) score += weight
        }

        return score
    }

    // Generar recomendaciones basadas en los factores de riesgo
    fun generateRecommendations(riskFactors: RiskFactorsRequest): List<String> {
        val recommendations = mutableListOf<String>()

        if (riskFactors.hipertension) {
            recommendations.add("Controle regularmente su presión arterial")
            recommendations.add("Reduzca el consumo de sal en su dieta")
        }

        if (riskFactors.diabetes) {
            recommendations.add("Mantenga un control estricto de sus niveles de glucosa")
            recommendations.add("Siga una dieta balanceada baja en azúcares")
        }

        if (riskFactors.colesterolElevado) {
            recommendations.add("Reduzca el consumo de grasas saturadas")
            recommendations.add("Incluya más fibra en su alimentación")
        }

        if (riskFactors.sobrepeso) {
            recommendations.add("Mantenga un peso saludable")
            recommendations.add("Consulte con un nutricionista")
        }

        if (riskFactors.sedentarismo) {
            recommendations.add("Realice al menos 30 minutos de ejercicio diario")
            recommendations.add("Incorpore actividad física en su rutina")
        }

        if (riskFactors.tabaquismo) {
            recommendations.add("Considere dejar de fumar")
            recommendations.add("Busque apoyo profesional para dejar el tabaco")
        }

        if (riskFactors.estresCronico) {
            recommendations.add("Practique técnicas de relajación")
            recommendations.add("Mantenga un equilibrio entre trabajo y descanso")
        }

        if (riskFactors.antecedentesFamiliares) {
            recommendations.add("Realice chequeos médicos regulares")
            recommendations.add("Informe a su médico sobre su historial familiar")
        }

        return recommendations
    }

    // Validar que la fecha de registro sea válida
    fun validateRegistrationDate(timestamp: Long): ValidationResult {
        val currentTime = System.currentTimeMillis()
        val oneHourAgo = currentTime - (60 * 60 * 1000) // 1 hora atrás
        val futureLimit = currentTime + (5 * 60 * 1000) // 5 minutos en el futuro

        return when {
            timestamp < oneHourAgo -> ValidationResult(false, "Fecha de registro muy antigua")
            timestamp > futureLimit -> ValidationResult(false, "Fecha de registro inválida")
            else -> ValidationResult(true)
        }
    }
}