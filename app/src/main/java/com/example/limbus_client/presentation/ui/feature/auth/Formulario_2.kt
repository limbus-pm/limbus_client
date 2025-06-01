package com.example.limbus_client.presentation.ui.feature.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.limbus_client.presentation.ui.component.StepIndicator
import com.example.limbus_client.presentation.viewmodel.PersonalDataFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formulario_2(
    onContinueClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onLoginClicked: () -> Unit,
    viewModel: PersonalDataFormViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
            // Indicador de progreso (puntos) - Using common component
            StepIndicator(currentStep = 2, totalSteps = 3)

            Spacer(modifier = Modifier.height(20.dp))

            // Título y subtítulo
            Text(
                text = "Personalice su experiencia",
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

            // Campo de fecha de nacimiento
            Text(
                text = "Fecha de nacimiento",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            OutlinedTextField(
                value = uiState.fechaNacimiento,
                onValueChange = { viewModel.onFechaNacimientoChanged(it) },
                placeholder = { Text("DD/MM/AAAA") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Fecha de nacimiento"
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { /* Abrir selector de fecha */ }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Seleccionar fecha"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                isError = uiState.fechaNacimientoError != null
            )

            if (uiState.fechaNacimientoError != null) {
                Text(
                    text = uiState.fechaNacimientoError!!,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start).padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de género
            Text(
                text = "Género",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = uiState.genero,
                    onValueChange = {},
                    readOnly = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Género"
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { viewModel.toggleGeneroDropdown() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = if (uiState.generoExpanded) "Cerrar" else "Abrir"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    isError = uiState.generoError != null
                )

                DropdownMenu(
                    expanded = uiState.generoExpanded,
                    onDismissRequest = { viewModel.toggleGeneroDropdown() },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    uiState.generosList.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                viewModel.onGeneroChanged(item)
                            }
                        )
                    }
                }
            }

            if (uiState.generoError != null) {
                Text(
                    text = uiState.generoError!!,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start).padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de altura
            Text(
                text = "Altura (cm)",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            OutlinedTextField(
                value = uiState.altura,
                onValueChange = { viewModel.onAlturaChanged(it) },
                placeholder = { Text("Ingrese su altura en cm") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Altura Icon"
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { /* Mostrar calculadora */ }) {
                        Text(text = "cm")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = uiState.alturaError != null
            )

            if (uiState.alturaError != null) {
                Text(
                    text = uiState.alturaError!!,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start).padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de peso
            Text(
                text = "Peso (kg)",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            OutlinedTextField(
                value = uiState.peso,
                onValueChange = { viewModel.onPesoChanged(it) },
                placeholder = { Text("Ingrese su peso en kg") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Peso Icon"
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { /* Mostrar calculadora */ }) {
                        Text(text = "kg")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = uiState.pesoError != null
            )

            if (uiState.pesoError != null) {
                Text(
                    text = uiState.pesoError!!,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start).padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Resultado IMC
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "IMC Icon",
                            tint = Color(0xFF3F51B5)
                        )
                        Text(
                            text = "Resultado IMC",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    Text(
                        text = if (uiState.imc > 0) {
                            "Tu Índice de Masa Corporal es de ${String.format("%.1f", uiState.imc)} - ${uiState.imcCategoria}"
                        } else {
                            "Tu Índice de Masa Corporal es de ####"
                        },
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botón de continuar
            Button(
                onClick = {
                    if (viewModel.validateForm()) {
                        onContinueClicked()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3F51B5)
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                enabled = uiState.isFormValid
            ) {
                Text(
                    text = "Continuar",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            if (uiState.message != null) {
                Text(
                    text = uiState.message!!,
                    color = if (uiState.message!!.contains("error", ignoreCase = true)) Color.Red else Color.Green,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Texto "¿Tengo una cuenta?"
            TextButton(onClick = onLoginClicked) {
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