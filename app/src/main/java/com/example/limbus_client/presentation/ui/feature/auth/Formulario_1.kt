// ========================================
// 1. Formulario_1.kt - UI actualizada con ViewModel
// ========================================
package com.example.limbus_client.presentation.ui.feature.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.limbus_client.presentation.ui.component.StepIndicator
import com.example.limbus_client.presentation.viewmodel.RegistrationViewModel
import com.example.limbus_client2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formulario_1(
    viewModel: RegistrationViewModel = viewModel(),
    onContinueClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onLoginClicked: () -> Unit
) {
    // Observar el estado del ViewModel
    val uiState by viewModel.uiState.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    // Manejar navegación automática cuando la validación es exitosa
    LaunchedEffect(uiState.step1Success) {
        if (uiState.step1Success) {
            onContinueClicked()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Registro") },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Indicador de progreso (puntos)
            StepIndicator(currentStep = 1, totalSteps = 3)

            Spacer(modifier = Modifier.height(20.dp))

            // Título y subtítulo
            Text(
                text = "Usuario nuevo",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Text(
                text = "Rellene el formulario para continuar",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Campo de nombre con validación
            Text(
                text = "Nombre",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { viewModel.onNombreChanged(it) },
                placeholder = { Text("Ingrese su nombre") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Nombre Icon"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                isError = uiState.nombreError != null,
                supportingText = {
                    uiState.nombreError?.let { error ->
                        Text(
                            text = error,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de apellido con validación
            Text(
                text = "Apellido",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            OutlinedTextField(
                value = uiState.apellido,
                onValueChange = { viewModel.onApellidoChanged(it) },
                placeholder = { Text("Ingrese su apellido") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Apellido Icon"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                isError = uiState.apellidoError != null,
                supportingText = {
                    uiState.apellidoError?.let { error ->
                        Text(
                            text = error,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de correo con validación
            Text(
                text = "Correo",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            OutlinedTextField(
                value = uiState.correo,
                onValueChange = { viewModel.onCorreoChanged(it) },
                placeholder = { Text("Ingrese su correo electrónico") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Icon"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = uiState.correoError != null,
                supportingText = {
                    uiState.correoError?.let { error ->
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
            Text(
                text = "Contraseña",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            OutlinedTextField(
                value = uiState.contrasena,
                onValueChange = { viewModel.onContrasenaChanged(it) },
                placeholder = { Text("Ingrese una contraseña") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password Icon"
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = if (passwordVisible)
                                painterResource(id = R.drawable.ic_visibility_on)
                            else
                                painterResource(id = R.drawable.ic_visibility_off),
                            contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = uiState.contrasenaError != null,
                supportingText = {
                    uiState.contrasenaError?.let { error ->
                        Text(
                            text = error,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de confirmar contraseña con validación
            Text(
                text = "Confirmar contraseña",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            OutlinedTextField(
                value = uiState.confirmarContrasena,
                onValueChange = { viewModel.onConfirmarContrasenaChanged(it) },
                placeholder = { Text("Vuelve a escribir tu contraseña") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Confirm Password Icon"
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            painter = if (confirmPasswordVisible)
                                painterResource(id = R.drawable.ic_visibility_on)
                            else
                                painterResource(id = R.drawable.ic_visibility_off),
                            contentDescription = if (confirmPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                        )
                    }
                },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = uiState.confirmarContrasenaError != null,
                supportingText = {
                    uiState.confirmarContrasenaError?.let { error ->
                        Text(
                            text = error,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Mostrar mensajes de éxito o error
            uiState.message?.let { message ->
                Text(
                    text = message,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = if (uiState.step1Success) Color.Green else Color.Red,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Botón de continuar
            Button(
                onClick = { viewModel.onContinueStep1Clicked() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3F51B5)
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                enabled = !uiState.isLoading &&
                        uiState.nombreError == null &&
                        uiState.apellidoError == null &&
                        uiState.correoError == null &&
                        uiState.contrasenaError == null &&
                        uiState.confirmarContrasenaError == null &&
                        uiState.nombre.isNotBlank() &&
                        uiState.apellido.isNotBlank() &&
                        uiState.correo.isNotBlank() &&
                        uiState.contrasena.isNotBlank() &&
                        uiState.confirmarContrasena.isNotBlank()
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text(
                        text = "Continuar",
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Texto "¿Tengo una cuenta?"
            TextButton(
                onClick = onLoginClicked,
                enabled = !uiState.isLoading
            ) {
                Text(
                    text = "¿Tengo una cuenta? Iniciar sesión",
                    color = Color(0xFF3F51B5),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}