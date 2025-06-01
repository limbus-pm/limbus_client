package com.example.limbus_client2.ui.feature.auth.welcome

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WelcomeViewModel : ViewModel() {
    // Este ViewModel está preparado para futuras funcionalidades
    // Por ahora está vacío ya que la pantalla de bienvenida es sencilla

    private val _uiState = MutableStateFlow(WelcomeUiState())
    val uiState: StateFlow<WelcomeUiState> = _uiState.asStateFlow()
}

data class WelcomeUiState(
    val isLoading: Boolean = false
)