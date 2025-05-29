package com.example.limbus_client.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.limbus_client.data.model.remote.RegistrationUiState
import com.example.limbus_client.data.repository.impl.PersonalDataValidationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PersonalDataFormViewModel(
    private val validationRepository: PersonalDataValidationRepository = PersonalDataValidationRepository()
) : ViewModel() {

    // Estados de la UI
    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    // Validar y actualizar fecha de nacimiento
    fun onFechaNacimientoChanged(fecha: String) {
        val formattedFecha = validationRepository.validateDateInput(fecha)
        val validation = validationRepository.validateFechaNacimiento(formattedFecha)

        _uiState.value = _uiState.value.copy(
            fechaNacimiento = formattedFecha,
            fechaNacimientoError = if (!validation.isValid) validation.errorMessage else null
        )

        updateFormValidation()
        calculateIMC()
    }

    // Seleccionar género
    fun onGeneroChanged(genero: String) {
        val validation = validationRepository.validateGenero(genero)

        _uiState.value = _uiState.value.copy(
            genero = genero,
            generoExpanded = false,
            generoError = if (!validation.isValid) validation.errorMessage else null
        )

        updateFormValidation()
    }

    // Toggle dropdown de género
    fun toggleGeneroDropdown() {
        _uiState.value = _uiState.value.copy(
            generoExpanded = !_uiState.value.generoExpanded
        )
    }

    // Validar y actualizar altura
    fun onAlturaChanged(altura: String) {
        val filteredAltura = validationRepository.validateNumericInput(altura)
        val validation = validationRepository.validateAltura(filteredAltura)

        _uiState.value = _uiState.value.copy(
            altura = filteredAltura,
            alturaError = if (!validation.isValid) validation.errorMessage else null
        )

        updateFormValidation()
        calculateIMC()
    }

    // Validar y actualizar peso
    fun onPesoChanged(peso: String) {
        val filteredPeso = validationRepository.validateNumericInput(peso)
        val validation = validationRepository.validatePeso(filteredPeso)

        _uiState.value = _uiState.value.copy(
            peso = filteredPeso,
            pesoError = if (!validation.isValid) validation.errorMessage else null
        )

        updateFormValidation()
        calculateIMC()
    }

    // Calcular IMC automáticamente
    private fun calculateIMC() {
        val currentState = _uiState.value

        if (currentState.altura.isNotBlank() && currentState.peso.isNotBlank()) {
            try {
                val imc = validationRepository.calculateIMC(currentState.altura, currentState.peso)
                val categoria = validationRepository.getIMCCategory(imc)

                _uiState.value = currentState.copy(
                    imc = imc,
                    imcCategoria = categoria
                )
            } catch (e: Exception) {
                _uiState.value = currentState.copy(
                    imc = 0.0,
                    imcCategoria = ""
                )
            }
        } else {
            _uiState.value = currentState.copy(
                imc = 0.0,
                imcCategoria = ""
            )
        }
    }

    // Actualizar validación completa del formulario
    private fun updateFormValidation() {
        val currentState = _uiState.value

        val isValid = currentState.fechaNacimientoError == null &&
                currentState.generoError == null &&
                currentState.alturaError == null &&
                currentState.pesoError == null &&
                currentState.fechaNacimiento.isNotBlank() &&
                currentState.genero.isNotBlank() &&
                currentState.altura.isNotBlank() &&
                currentState.peso.isNotBlank()

        _uiState.value = currentState.copy(
            isFormValid = isValid,
            message = null
        )
    }

    // Validar formulario completo antes de continuar
    fun validateForm(): Boolean {
        val currentState = _uiState.value

        val validation = validationRepository.validateCompleteForm(
            currentState.fechaNacimiento,
            currentState.genero,
            currentState.altura,
            currentState.peso
        )

        if (!validation.isValid) {
            _uiState.value = currentState.copy(
                message = validation.errorMessage,
                isFormValid = false
            )
            return false
        }

        _uiState.value = currentState.copy(
            message = "Datos válidos. Continuando...",
            isFormValid = true
        )

        return true
    }

    // Limpiar mensajes
    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }

    // Resetear formulario
    fun resetForm() {
        _uiState.value = RegistrationUiState()
    }

    // Obtener datos del formulario para enviar
    fun getFormData(): Map<String, Any> {
        val currentState = _uiState.value
        return mapOf(
            "fechaNacimiento" to currentState.fechaNacimiento,
            "genero" to currentState.genero,
            "altura" to (currentState.altura.toIntOrNull() ?: 0), // Cambio aquí para que coincida con el DTO
            "peso" to (currentState.peso.toDoubleOrNull() ?: 0.0),
            "imc" to currentState.imc,
            "imcCategoria" to currentState.imcCategoria
        )
    }

    // Método para obtener el DTO para peticiones de red
    fun getPersonalDataRequest(): com.example.limbus_client.data.model.remote.RegistrationPersonalDataRequest? {
        val currentState = _uiState.value

        return try {
            com.example.limbus_client.data.model.remote.RegistrationPersonalDataRequest(
                fechaNacimiento = currentState.fechaNacimiento,
                genero = currentState.genero,
                altura = currentState.altura.toInt(),
                peso = currentState.peso.toDouble(),
                imc = currentState.imc
            )
        } catch (e: NumberFormatException) {
            null
        }
    }

    // Método para manejar errores de red
    fun handleNetworkError(error: String) {
        _uiState.value = _uiState.value.copy(
            message = "Error de conexión: $error",
            isLoading = false
        )
    }

    // Método para mostrar loading
    fun setLoading(isLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = isLoading)
    }
}