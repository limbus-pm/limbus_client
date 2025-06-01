package com.example.limbus_client.utils

object RegistrationConstants {
    // Límites de campos
    const val MIN_NAME_LENGTH = 2
    const val MAX_NAME_LENGTH = 50
    const val MIN_PASSWORD_LENGTH = 8
    const val MAX_PASSWORD_LENGTH = 50
    const val MAX_EMAIL_LENGTH = 100
    const val MIN_EMAIL_LENGTH = 5

    // Patrones
    const val SPECIAL_CHARACTERS = "!@#$%^&*()_+-=[]{}|;:,.<>?"

    // Mensajes de error predefinidos
    object ErrorMessages {
        const val REQUIRED_FIELD = "Este campo es requerido"
        const val INVALID_EMAIL = "Formato de correo inválido"
        const val PASSWORDS_DONT_MATCH = "Las contraseñas no coinciden"
        const val PASSWORD_TOO_WEAK = "La contraseña debe ser más segura"
        const val NAME_TOO_SHORT = "Debe tener al menos 2 caracteres"
        const val NAME_TOO_LONG = "Máximo 50 caracteres permitidos"
        const val ONLY_LETTERS_ALLOWED = "Solo se permiten letras"
        const val EMAIL_ALREADY_EXISTS = "Este correo ya está registrado"
        const val CONNECTION_ERROR = "Error de conexión. Intente nuevamente"
        const val SERVER_ERROR = "Error del servidor. Intente más tarde"
    }

    // Mensajes de éxito
    object SuccessMessages {
        const val STEP_1_COMPLETED = "Datos válidos. Continúe con el siguiente paso"
        const val EMAIL_AVAILABLE = "Correo disponible"
        const val VALIDATION_PASSED = "Validación exitosa"
    }

    // Timeouts y delays
    const val NETWORK_TIMEOUT = 30_000L // 30 segundos
    const val VALIDATION_DELAY = 2_000L // 2 segundos
    const val SUCCESS_DELAY = 1_500L // 1.5 segundos
}