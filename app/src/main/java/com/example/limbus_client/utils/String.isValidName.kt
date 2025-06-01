
// ========================================
// 8. Extensiones útiles y validaciones adicionales
// ========================================
package com.example.limbus_client.utils

// Extensiones para String
fun String.isValidName(): Boolean {
    return this.trim().length >= 2 &&
            this.all { it.isLetter() || it.isWhitespace() } &&
            !this.startsWith(" ") &&
            !this.endsWith(" ")
}

fun String.capitalizeNames(): String {
    return this.trim()
        .split("\\s+".toRegex())
        .joinToString(" ") { word ->
            word.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
}

fun String.isStrongPassword(): Boolean {
    return this.length >= 8 &&
            this.any { it.isDigit() } &&
            this.any { it.isLowerCase() } &&
            this.any { it.isUpperCase() } &&
            this.any { it in "!@#$%^&*()_+-=[]{}|;:,.<>?" } &&
            !this.contains(" ")
}

fun String.sanitizeEmail(): String {
    return this.trim().lowercase()
}