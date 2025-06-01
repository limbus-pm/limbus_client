// ========================================
// 2. RegistrationViewModel.kt - ViewModel específico
// ========================================
package com.example.limbus_client.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.limbus_client.data.repository.impl.RegistrationValidationRepository
import com.example.limbus_client.domain.usecase.RegistrationResult
import com.example.limbus_client.domain.usecase.RegistrationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val registrationUseCase: RegistrationUseCase =
        RegistrationUseCase(RegistrationValidationRepository())
) : ViewModel() {

    // Estados de la UI
    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    // Validar nombre en tiempo real
    fun onNombreChanged(nombre: String) {
        val validation = RegistrationValidationRepository().validateNombre(nombre)
        _uiState.value = _uiState.value.copy(
            nombre = nombre,
            nombreError = if (!validation.isValid) validation.errorMessage else null,
            message = null
        )
    }

    // Validar apellido en tiempo real
    fun onApellidoChanged(apellido: String) {
        val validation = RegistrationValidationRepository().validateApellido(apellido)
        _uiState.value = _uiState.value.copy(
            apellido = apellido,
            apellidoError = if (!validation.isValid) validation.errorMessage else null,
            message = null
        )
    }

    // Validar correo en tiempo real
    fun onCorreoChanged(correo: String) {
        val validation = RegistrationValidationRepository().validateCorreo(correo)
        _uiState.value = _uiState.value.copy(
            correo = correo,
            correoError = if (!validation.isValid) validation.errorMessage else null,
            message = null
        )
    }

    // Validar contraseña en tiempo real
    fun onContrasenaChanged(contrasena: String) {
        val validation = RegistrationValidationRepository().validateContrasena(contrasena)
        _uiState.value = _uiState.value.copy(
            contrasena = contrasena,
            contrasenaError = if (!validation.isValid) validation.errorMessage else null,
            message = null
        )

        // Revalidar confirmación de contraseña si ya tiene datos
        if (_uiState.value.confirmarContrasena.isNotBlank()) {
            onConfirmarContrasenaChanged(_uiState.value.confirmarContrasena)
        }
    }

    // Validar confirmación de contraseña en tiempo real
    fun onConfirmarContrasenaChanged(confirmarContrasena: String) {
        val validation = RegistrationValidationRepository()
            .validateConfirmarContrasena(confirmarContrasena, _uiState.value.contrasena)
        _uiState.value = _uiState.value.copy(
            confirmarContrasena = confirmarContrasena,
            confirmarContrasenaError = if (!validation.isValid) validation.errorMessage else null,
            message = null
        )
    }

    // Procesar Step 1 del registro
    fun onContinueStep1Clicked() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, message = null)

            val result = registrationUseCase.executeStep1(
                nombre = _uiState.value.nombre,
                apellido = _uiState.value.apellido,
                correo = _uiState.value.correo,
                contrasena = _uiState.value.contrasena,
                confirmarContrasena = _uiState.value.confirmarContrasena
            )

            when (result) {
                is RegistrationResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        step1Success = true,
                        message = result.message
                    )
                }
                is RegistrationResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        step1Success = false,
                        message = result.message
                    )
                }
                else -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            }
        }
    }

    // Limpiar estado cuando sea necesario
    fun clearState() {
        _uiState.value = RegistrationUiState()
    }
}

// Estado de la UI del registro
data class RegistrationUiState(
    val nombre: String = "",
    val apellido: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val confirmarContrasena: String = "",
    val nombreError: String? = null,
    val apellidoError: String? = null,
    val correoError: String? = null,
    val contrasenaError: String? = null,
    val confirmarContrasenaError: String? = null,
    val isLoading: Boolean = false,
    val step1Success: Boolean = false,
    val message: String? = null
)