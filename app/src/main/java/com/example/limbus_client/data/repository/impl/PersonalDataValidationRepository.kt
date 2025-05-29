package com.example.limbus_client.data.repository.impl

import android.util.Patterns
import com.example.limbus_client.data.model.remote.ValidationResult
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.pow

class PersonalDataValidationRepository {

    // Validación para fecha de nacimiento
    fun validateFechaNacimiento(fecha: String): ValidationResult {
        if (fecha.isBlank()) {
            return ValidationResult(false, "La fecha de nacimiento es requerida")
        }

        // Validar formato DD/MM/YYYY
        val regex = Regex("^\\d{2}/\\d{2}/\\d{4}$")
        if (!regex.matches(fecha)) {
            return ValidationResult(false, "Formato de fecha inválido. Use DD/MM/AAAA")
        }

        try {
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            formatter.isLenient = false
            val fechaNacimiento = formatter.parse(fecha)

            val calendar = Calendar.getInstance()
            val hoy = calendar.time
            calendar.add(Calendar.YEAR, -100) // Máximo 100 años
            val fechaMinima = calendar.time

            calendar.time = hoy
            calendar.add(Calendar.YEAR, -13) // Mínimo 13 años
            val fechaMaxima = calendar.time

            when {
                fechaNacimiento == null -> return ValidationResult(false, "Fecha inválida")
                fechaNacimiento.after(fechaMaxima) -> return ValidationResult(false, "Debe ser mayor de 13 años")
                fechaNacimiento.before(fechaMinima) -> return ValidationResult(false, "Fecha no válida")
                else -> return ValidationResult(true)
            }
        } catch (e: Exception) {
            return ValidationResult(false, "Fecha inválida")
        }
    }

    // Validación para género
    fun validateGenero(genero: String): ValidationResult {
        val generosValidos = listOf("Masculino", "Femenino", "Prefiero no decirlo")

        return if (genero.isBlank()) {
            ValidationResult(false, "Debe seleccionar un género")
        } else if (!generosValidos.contains(genero)) {
            ValidationResult(false, "Género no válido")
        } else {
            ValidationResult(true)
        }
    }

    // Validación para altura
    fun validateAltura(altura: String): ValidationResult {
        if (altura.isBlank()) {
            return ValidationResult(false, "La altura es requerida")
        }

        try {
            val alturaValue = altura.toInt()
            return when {
                alturaValue < 50 -> ValidationResult(false, "La altura mínima es 50 cm")
                alturaValue > 250 -> ValidationResult(false, "La altura máxima es 250 cm")
                else -> ValidationResult(true)
            }
        } catch (e: NumberFormatException) {
            return ValidationResult(false, "Altura debe ser un número válido")
        }
    }

    // Validación para peso
    fun validatePeso(peso: String): ValidationResult {
        if (peso.isBlank()) {
            return ValidationResult(false, "El peso es requerido")
        }

        try {
            val pesoValue = peso.toDouble()
            return when {
                pesoValue < 20 -> ValidationResult(false, "El peso mínimo es 20 kg")
                pesoValue > 300 -> ValidationResult(false, "El peso máximo es 300 kg")
                else -> ValidationResult(true)
            }
        } catch (e: NumberFormatException) {
            return ValidationResult(false, "Peso debe ser un número válido")
        }
    }

    // Cálculo de IMC
    fun calculateIMC(altura: String, peso: String): Double {
        return try {
            val alturaMetros = altura.toDouble() / 100
            val pesoKg = peso.toDouble()
            pesoKg / (alturaMetros.pow(2))
        } catch (e: Exception) {
            0.0
        }
    }

    // Categoría de IMC
    fun getIMCCategory(imc: Double): String {
        return when {
            imc < 18.5 -> "Bajo peso"
            imc < 25 -> "Peso normal"
            imc < 30 -> "Sobrepeso"
            else -> "Obesidad"
        }
    }

    // Validación completa del formulario
    fun validateCompleteForm(
        fechaNacimiento: String,
        genero: String,
        altura: String,
        peso: String
    ): ValidationResult {
        val fechaValidation = validateFechaNacimiento(fechaNacimiento)
        if (!fechaValidation.isValid) return fechaValidation

        val generoValidation = validateGenero(genero)
        if (!generoValidation.isValid) return generoValidation

        val alturaValidation = validateAltura(altura)
        if (!alturaValidation.isValid) return alturaValidation

        val pesoValidation = validatePeso(peso)
        if (!pesoValidation.isValid) return pesoValidation

        return ValidationResult(true, "Formulario válido")
    }

    // Validación solo para números
    fun validateNumericInput(input: String): String {
        return input.filter { it.isDigit() || it == '.' }
    }

    // Validación de fecha en tiempo real
    fun validateDateInput(input: String): String {
        // Permitir solo números y /
        val filtered = input.filter { it.isDigit() || it == '/' }

        // Formatear automáticamente DD/MM/YYYY
        return when (filtered.length) {
            in 0..2 -> filtered
            in 3..4 -> if (filtered[2] != '/') filtered.substring(0, 2) + "/" + filtered.substring(2) else filtered
            in 5..6 -> filtered
            in 7..10 -> if (filtered[5] != '/') filtered.substring(0, 5) + "/" + filtered.substring(5) else filtered
            else -> filtered.substring(0, 10)
        }
    }
}