
// ========================================
// 10. TestDataProvider.kt - Datos de prueba
// ========================================
package com.example.limbus_client.data.testdata

object RegistrationTestData {
    // Datos válidos para pruebas
    val validTestData = mapOf(
        "nombre" to "Juan Carlos",
        "apellido" to "Pérez González",
        "correo" to "juan.perez@ejemplo.com",
        "contrasena" to "MiPassword123!",
        "confirmarContrasena" to "MiPassword123!"
    )

    // Correos que simulan estar ya registrados
    val existingEmails = listOf(
        "admin@ejemplo.com",
        "usuario@test.com",
        "test@limbus.com",
        "registro@existente.com"
    )

    // Correos que simulan errores del servidor
    val errorEmails = listOf(
        "error@servidor.com",
        "timeout@red.com",
        "fallo@sistema.com"
    )

    // Datos de prueba para diferentes escenarios
    val testScenarios = mapOf(
        "usuario_valido" to mapOf(
            "nombre" to "María",
            "apellido" to "García",
            "correo" to "maria.garcia@correo.com",
            "contrasena" to "Segura123!",
            "confirmarContrasena" to "Segura123!"
        ),
        "correo_existente" to mapOf(
            "nombre" to "Pedro",
            "apellido" to "López",
            "correo" to "admin@ejemplo.com", // Este correo ya existe
            "contrasena" to "Password123!",
            "confirmarContrasena" to "Password123!"
        ),
        "contrasenas_no_coinciden" to mapOf(
            "nombre" to "Ana",
            "apellido" to "Martínez",
            "correo" to "ana.martinez@test.com",
            "contrasena" to "Password123!",
            "confirmarContrasena" to "Password456!" // No coincide
        ),
        "contrasena_debil" to mapOf(
            "nombre" to "Luis",
            "apellido" to "Rodríguez",
            "correo" to "luis.rodriguez@mail.com",
            "contrasena" to "123456", // Muy débil
            "confirmarContrasena" to "123456"
        )
    )
}
