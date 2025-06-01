
// ========================================
// 3. RegistrationValidationRepository.kt - Validaciones específicas
// ========================================
package com.example.limbus_client.data.repository.impl

import android.util.Patterns

class RegistrationValidationRepository {

    // Validación para nombre
    fun validateNombre(nombre: String): ValidationResult {
        return when {
            nombre.isBlank() -> ValidationResult(false, "El nombre es requerido")
            nombre.length < 2 -> ValidationResult(false, "El nombre debe tener al menos 2 caracteres")
            nombre.length > 50 -> ValidationResult(false, "El nombre es demasiado largo")
            !nombre.all { it.isLetter() || it.isWhitespace() } ->
                ValidationResult(false, "El nombre solo puede contener letras")
            nombre.trim().split("\\s+".toRegex()).size > 3 ->
                ValidationResult(false, "Máximo 3 nombres permitidos")
            else -> ValidationResult(true)
        }
    }

    // Validación para apellido
    fun validateApellido(apellido: String): ValidationResult {
        return when {
            apellido.isBlank() -> ValidationResult(false, "El apellido es requerido")
            apellido.length < 2 -> ValidationResult(false, "El apellido debe tener al menos 2 caracteres")
            apellido.length > 50 -> ValidationResult(false, "El apellido es demasiado largo")
            !apellido.all { it.isLetter() || it.isWhitespace() } ->
                ValidationResult(false, "El apellido solo puede contener letras")
            apellido.trim().split("\\s+".toRegex()).size > 3 ->
                ValidationResult(false, "Máximo 3 apellidos permitidos")
            else -> ValidationResult(true)
        }
    }

    // Validación para correo (reutilizando la lógica del AuthValidationRepository)
    fun validateCorreo(correo: String): ValidationResult {
        return when {
            correo.isBlank() -> ValidationResult(false, "El correo electrónico es requerido")
            correo.length > 100 -> ValidationResult(false, "El correo es demasiado largo")
            !Patterns.EMAIL_ADDRESS.matcher(correo).matches() ->
                ValidationResult(false, "Formato de correo inválido")
            correo.contains("..") -> ValidationResult(false, "El correo no puede tener puntos consecutivos")
            correo.length < 5 -> ValidationResult(false, "El correo es demasiado corto")
            else -> ValidationResult(true)
        }
    }

    // Validación para contraseña (más estricta que login)
    fun validateContrasena(contrasena: String): ValidationResult {
        return when {
            contrasena.isBlank() -> ValidationResult(false, "La contraseña es requerida")
            contrasena.length < 8 -> ValidationResult(false, "La contraseña debe tener al menos 8 caracteres")
            contrasena.length > 50 -> ValidationResult(false, "La contraseña es demasiado larga")
            !contrasena.any { it.isDigit() } ->
                ValidationResult(false, "La contraseña debe contener al menos un número")
            !contrasena.any { it.isLowerCase() } ->
                ValidationResult(false, "La contraseña debe contener al menos una letra minúscula")
            !contrasena.any { it.isUpperCase() } ->
                ValidationResult(false, "La contraseña debe contener al menos una letra mayúscula")
            !contrasena.any { it in "!@#$%^&*()_+-=[]{}|;:,.<>?" } ->
                ValidationResult(false, "La contraseña debe contener al menos un carácter especial")
            contrasena.contains(" ") -> ValidationResult(false, "La contraseña no puede contener espacios")
            else -> ValidationResult(true)
        }
    }

    // Validación para confirmar contraseña
    fun validateConfirmarContrasena(confirmarContrasena: String, contrasenaOriginal: String): ValidationResult {
        return when {
            confirmarContrasena.isBlank() ->
                ValidationResult(false, "Debe confirmar la contraseña")
            confirmarContrasena != contrasenaOriginal ->
                ValidationResult(false, "Las contraseñas no coinciden")
            else -> ValidationResult(true)
        }
    }

    // Validación completa del Step 1
    fun validateCompleteStep1(
        nombre: String,
        apellido: String,
        correo: String,
        contrasena: String,
        confirmarContrasena: String
    ): List<ValidationResult> {
        return listOf(
            validateNombre(nombre),
            validateApellido(apellido),
            validateCorreo(correo),
            validateContrasena(contrasena),
            validateConfirmarContrasena(confirmarContrasena, contrasena)
        )
    }

    // Validación de unicidad de correo (simulada)
    fun validateEmailUniqueness(correo: String): ValidationResult {
        // Lista de correos "ya registrados" para simular
        val existingEmails = listOf(
            "admin@ejemplo.com",
            "usuario@test.com",
            "test@limbus.com"
        )

        return if (existingEmails.contains(correo.lowercase())) {
            ValidationResult(false, "Este correo ya está registrado")
        } else {
            ValidationResult(true)
        }
    }
}