// ========================================
// 1. LoginScreen.kt - UI con validaciones en tiempo real
// ========================================
package com.example.limbus_client.presentation.ui.feature.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.limbus_client.presentation.viewmodel.LoginViewModel
import com.example.limbus_client2.R

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onGoogleLoginClicked: (String) -> Unit = {}
) {
    // Observar el estado del ViewModel
    val uiState by viewModel.uiState.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }

    // Manejar navegación automática cuando el login es exitoso
    LaunchedEffect(uiState.loginSuccess) {
        if (uiState.loginSuccess) {
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // Logo y título
                Image(
                    painter = painterResource(id = R.drawable.logo2),
                    contentDescription = "Logo Limbus",
                    modifier = Modifier.size(60.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Limbus",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "¡Comience su viaje hacia una mejor salud!",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Campo de correo electrónico con validación
                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    label = { Text("Correo electrónico") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email Icon"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = uiState.emailError != null,
                    supportingText = {
                        uiState.emailError?.let { error ->
                            Text(
                                text = error,
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de contraseña con validación
                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    label = { Text("Contraseña") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Password Icon"
                        )
                    },
                    trailingIcon = null,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    isError = uiState.passwordError != null,
                    supportingText = {
                        uiState.passwordError?.let { error ->
                            Text(
                                text = error,
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }
                    }
                )

                // Checkbox para mostrar/ocultar contraseña
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                ) {
                    Checkbox(
                        checked = passwordVisible,
                        onCheckedChange = { passwordVisible = it }
                    )
                    Text(
                        text = "Mostrar contraseña",
                        fontSize = 14.sp
                    )
                }

                // Olvidé mi contraseña
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { viewModel.onForgotPasswordClicked() },
                        enabled = !uiState.isLoading
                    ) {
                        Text(
                            text = "¿Olvidó su contraseña?",
                            fontSize = 14.sp,
                            color = Color(0xFF3F51B5)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botón de inicio de sesión
                Button(
                    onClick = { viewModel.onLoginClicked() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3F51B5)
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    enabled = !uiState.isLoading &&
                            uiState.emailError == null &&
                            uiState.passwordError == null &&
                            uiState.email.isNotBlank() &&
                            uiState.password.isNotBlank()
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text(
                            text = "Iniciar sesión",
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón de inicio de sesión con Google
                OutlinedButton(
                    onClick = {
                        // Simular token de Google para prueba
                        val googleToken = "google_token_example_${System.currentTimeMillis()}"
                        viewModel.onGoogleLoginClicked(googleToken)
                        onGoogleLoginClicked(googleToken)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    border = ButtonDefaults.outlinedButtonBorder,
                    enabled = !uiState.isLoading
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        // Aquí deberías tener un icono de Google
                        Spacer(modifier = Modifier.size(24.dp))

                        Text(
                            text = "Continuar con Google",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ¿No tiene una cuenta?
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "¿No tiene una cuenta?",
                        color = Color.Gray
                    )
                    TextButton(
                        onClick = {
                            viewModel.onRegisterClicked()
                            onNavigateToRegister()
                        },
                        enabled = !uiState.isLoading
                    ) {
                        Text(
                            text = "Regístrese",
                            color = Color(0xFF3F51B5),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar mensajes de éxito o error
                uiState.message?.let { message ->
                    Text(
                        text = message,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = if (uiState.loginSuccess) Color.Green else Color.Red,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                // Texto de términos y condiciones
                Text(
                    text = "Al iniciar sesión, acepto las Condiciones del servicio y la Política de privacidad de la aplicación",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }
        }
    }
}

