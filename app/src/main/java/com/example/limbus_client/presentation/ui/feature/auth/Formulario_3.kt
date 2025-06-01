package com.example.limbus_client.presentation.ui.feature.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.limbus_client.presentation.ui.component.StepIndicator
import com.example.limbus_client.data.model.remote.RiskFactorsRequest
import com.example.limbus_client.data.repository.impl.RiskFactorsValidationRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formulario_3(
    onFinishClicked: (RiskFactorsRequest) -> Unit,
    onBackClicked: () -> Unit,
    onLoginClicked: () -> Unit
) {
    // Repositorio de validación
    val validationRepository = remember { RiskFactorsValidationRepository() }

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
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Indicador de progreso (puntos) - Using common component
            StepIndicator(currentStep = 3, totalSteps = 3)

            Spacer(modifier = Modifier.height(20.dp))

            // Título y subtítulo
            Text(
                text = "Factores de riesgo",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Text(
                text = "Seleccione las condiciones que apliquen",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Lista de factores de riesgo con checkboxes
            val riskFactors = listOf(
                "Hipertensión",
                "Diabetes",
                "Colesterol elevado",
                "Antecedentes familiares",
                "Sobrepeso/Obesidad",
                "Sedentarismo",
                "Tabaquismo",
                "Estrés crónico"
            )

            // Estado para los checkboxes
            val checkboxStates = remember {
                mutableStateOf(riskFactors.associateWith { false })
            }

            riskFactors.forEach { factor ->
                RiskFactorCheckbox(
                    text = factor,
                    checked = checkboxStates.value[factor] ?: false,
                    onCheckedChange = { isChecked ->
                        checkboxStates.value = checkboxStates.value.toMutableMap().apply {
                            this[factor] = isChecked
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botón de finalizar
            Button(
                onClick = {
                    val currentStates = checkboxStates.value
                    val riskFactorsData = RiskFactorsRequest(
                        hipertension = currentStates["Hipertensión"] ?: false,
                        diabetes = currentStates["Diabetes"] ?: false,
                        colesterolElevado = currentStates["Colesterol elevado"] ?: false,
                        antecedentesFamiliares = currentStates["Antecedentes familiares"] ?: false,
                        sobrepeso = currentStates["Sobrepeso/Obesidad"] ?: false,
                        sedentarismo = currentStates["Sedentarismo"] ?: false,
                        tabaquismo = currentStates["Tabaquismo"] ?: false,
                        estresCronico = currentStates["Estrés crónico"] ?: false,
                        fechaRegistro = System.currentTimeMillis()
                    )

                    onFinishClicked(riskFactorsData)
                },
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

@Composable
private fun RiskFactorCheckbox(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = text,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}