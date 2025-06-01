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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.limbus_client.presentation.ui.component.StepIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formulario_2(
    onContinueClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onLoginClicked: () -> Unit
) {
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

            // Campos del formulario
            var fechaNacimiento by remember { mutableStateOf("16/06/2006") }
            var genero by remember { mutableStateOf("Masculino") }
            var altura by remember { mutableStateOf("185") }
            var peso by remember { mutableStateOf("90") }
            var expanded by remember { mutableStateOf(false) }
            val generos = listOf("Masculino", "Femenino", "Prefiero no decirlo")

            // Campo de fecha de nacimiento
            Text(
                text = "Fecha de nacimiento",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            OutlinedTextField(
                value = fechaNacimiento,
                onValueChange = { fechaNacimiento = it },
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
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de género
            Text(
                text = "Género",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = genero,
                    onValueChange = {},
                    readOnly = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Género"
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = if (expanded) "Cerrar" else "Abrir"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    generos.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                genero = item
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de altura
            Text(
                text = "Altura (cm)",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            OutlinedTextField(
                value = altura,
                onValueChange = { if (it.all { char -> char.isDigit() }) altura = it },
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de peso
            Text(
                text = "Peso (kg)",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            OutlinedTextField(
                value = peso,
                onValueChange = { if (it.all { char -> char.isDigit() }) peso = it },
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

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
                        text = "Tu Índice de Masa Corporal es de ####",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botón de continuar
            Button(
                onClick = onContinueClicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3F51B5)
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Continuar",
                    modifier = Modifier.padding(vertical = 8.dp)
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