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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.limbus_client.presentation.ui.component.StepIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formulario_5(
    onCompleteRegistrationClicked: () -> Unit,
    onBackClicked: () -> Unit
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
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Step indicator - Final step
            StepIndicator(currentStep = 5, totalSteps = 5)

            Spacer(modifier = Modifier.height(20.dp))

            // Title and subtitle
            Text(
                text = "Preferencias de la aplicación",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Text(
                text = "Configure sus preferencias para completar el registro",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Notifications section
            Text(
                text = "Notificaciones",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Receive notifications toggle
            val notificationsEnabled = remember { mutableStateOf(true) }
            PreferenceToggleItem(
                title = "Recibir notificaciones",
                checked = notificationsEnabled.value,
                onCheckedChange = { notificationsEnabled.value = it }
            )

            // Training alerts toggle
            val trainingAlertsEnabled = remember { mutableStateOf(true) }
            PreferenceToggleItem(
                title = "Recibir alertas sobre entrenamientos, logros y novedades",
                checked = trainingAlertsEnabled.value,
                onCheckedChange = { trainingAlertsEnabled.value = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // App Configuration section
            Text(
                text = "Configuración de la aplicación",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Auto updates toggle
            val autoUpdatesEnabled = remember { mutableStateOf(true) }
            PreferenceToggleItem(
                title = "Actualizaciones automáticas",
                checked = autoUpdatesEnabled.value,
                onCheckedChange = { autoUpdatesEnabled.value = it }
            )

            // Training progress updates toggle
            val trainingProgressUpdatesEnabled = remember { mutableStateOf(true) }
            PreferenceToggleItem(
                title = "Actualizar planes de entrenamiento según el progreso",
                checked = trainingProgressUpdatesEnabled.value,
                onCheckedChange = { trainingProgressUpdatesEnabled.value = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Anonymous data sharing toggle
            val shareAnonymousData = remember { mutableStateOf(false) }
            PreferenceToggleItem(
                title = "Compartir datos anónimos",
                subtitle = "Ayúdanos a mejorar con datos anónimos de uso",
                checked = shareAnonymousData.value,
                onCheckedChange = { shareAnonymousData.value = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Terms and conditions section
            Text(
                text = "Términos y condiciones",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Accept terms checkbox
            val termsAccepted = remember { mutableStateOf(false) }
            PreferenceCheckboxItem(
                text = "Acepto los términos y condiciones",
                checked = termsAccepted.value,
                onCheckedChange = { termsAccepted.value = it }
            )

            // Accept privacy policy checkbox
            val privacyPolicyAccepted = remember { mutableStateOf(false) }
            PreferenceCheckboxItem(
                text = "Acepto las políticas de privacidad",
                checked = privacyPolicyAccepted.value,
                onCheckedChange = { privacyPolicyAccepted.value = it }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Complete registration button - Enabled only if terms and privacy are accepted
            Button(
                onClick = onCompleteRegistrationClicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3F51B5),
                    disabledContainerColor = Color.Gray
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                enabled = termsAccepted.value && privacyPolicyAccepted.value
            ) {
                Text(
                    text = "Completar registro",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun PreferenceToggleItem(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    subtitle: String? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 16.sp
            )
            subtitle?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
private fun PreferenceCheckboxItem(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
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