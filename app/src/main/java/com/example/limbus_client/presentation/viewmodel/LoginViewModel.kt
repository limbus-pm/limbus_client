// ========================================
// 2. LoginViewModel.kt - Lógica de negocio
// ========================================
package com.example.limbus_client.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.limbus_client.data.repository.impl.AuthValidationRepository
import com.example.limbus_client.domain.usecase.AuthResult
import com.example.limbus_client.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authUseCase: AuthUseCase = AuthUseCase(AuthValidationRepository())
) : ViewModel() {

    // Estados de la UI
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Validar email en tiempo real
    fun onEmailChanged(email: String) {
        val validation = AuthValidationRepository().validateEmail(email)
        _uiState.value = _uiState.value.copy(
            email = email,
            emailError = if (!validation.isValid) validation.errorMessage else null,
            message = null // Limpiar mensajes anteriores
        )
    }

    // Validar contraseña en tiempo real
    fun onPasswordChanged(password: String) {
        val validation = AuthValidationRepository().validatePassword(password)
        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = if (!validation.isValid) validation.errorMessage else null,
            message = null // Limpiar mensajes anteriores
        )
    }

    // Ejecutar login
    fun onLoginClicked() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, message = null)

            val result = authUseCase.executeLogin(
                _uiState.value.email,
                _uiState.value.password
            )

            when (result) {
                is AuthResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        loginSuccess = true,
                        message = result.message
                    )
                }
                is AuthResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        loginSuccess = false,
                        message = result.message
                    )
                }
                else -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            }
        }
    }

    // Ejecutar registro
    fun onRegisterClicked() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, message = null)

            val result = authUseCase.executeRegister(
                _uiState.value.email,
                _uiState.value.password
            )

            when (result) {
                is AuthResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        message = result.message
                    )
                }
                is AuthResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        message = result.message
                    )
                }
                else -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            }
        }
    }

    // Ejecutar recuperación de contraseña
    fun onForgotPasswordClicked() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, message = null)

            val result = authUseCase.executeForgotPassword(_uiState.value.email)

            when (result) {
                is AuthResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        message = result.message
                    )
                }
                is AuthResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        message = result.message
                    )
                }
                else -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            }
        }
    }

    // Ejecutar login con Google
    fun onGoogleLoginClicked(googleToken: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, message = null)

            val result = authUseCase.executeGoogleLogin(googleToken)

            when (result) {
                is AuthResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        loginSuccess = true,
                        message = result.message
                    )
                }
                is AuthResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        loginSuccess = false,
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
        _uiState.value = LoginUiState()
    }
}

// Estado de la UI
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,
    val message: String? = null
)
