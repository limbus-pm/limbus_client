package com.example.limbus_client.presentation.ui.feature.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun RegisterFoodScreen(
    onBackClicked: () -> Unit,
    onFoodRegistered: (Food) -> Unit = {}
) {
    // Estados para los campos del formulario
    var foodName by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf("🥛") }
    var selectedColor by remember { mutableStateOf(FoodColor.Blue) }

    // Estados para validación
    var isFormValid by remember { mutableStateOf(false) }
    var showErrors by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    // Estados específicos para errores de cada campo
    var foodNameError by remember { mutableStateOf("") }
    var caloriesError by remember { mutableStateOf("") }
    var proteinError by remember { mutableStateOf("") }

    // SnackBar para mostrar mensajes
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Lista de iconos disponibles
    val availableIcons = listOf(
        "🍎", "🥕", "🥒", "🍋", "🥚", "🍞",
        "🫐", "🌽", "🍅", "🥛", "🧀", "🍌"
    )

    // Lista de colores disponibles
    val availableColors = listOf(
        FoodColor.Blue,
        FoodColor.Yellow,
        FoodColor.Orange,
        FoodColor.Green,
        FoodColor.Purple,
        FoodColor.Pink,
        FoodColor.Amber
    )

    // Función para validar un campo numérico
    fun validateNumericField(value: String, fieldName: String): String {
        return when {
            value.isBlank() -> "El campo $fieldName es obligatorio"
            value.toDoubleOrNull() == null -> "Ingrese un número válido"
            value.toDouble() < 0 -> "El valor no puede ser negativo"
            value.toDouble() > 10000 -> "El valor es demasiado alto"
            else -> ""
        }
    }

    // Función para validar el nombre del alimento
    fun validateFoodName(name: String): String {
        return when {
            name.isBlank() -> "El nombre del alimento es obligatorio"
            name.length < 2 -> "El nombre debe tener al menos 2 caracteres"
            name.length > 100 -> "El nombre es demasiado largo"
            else -> ""
        }
    }

    // Validación en tiempo real
    LaunchedEffect(foodName, calories, protein, showErrors) {
        if (showErrors) {
            foodNameError = validateFoodName(foodName.trim())
            caloriesError = validateNumericField(calories, "Calorías")
            proteinError = validateNumericField(protein, "Proteínas")
        }

        isFormValid = foodName.trim().isNotBlank() &&
                validateFoodName(foodName.trim()).isEmpty() &&
                validateNumericField(calories, "Calorías").isEmpty() &&
                validateNumericField(protein, "Proteínas").isEmpty()
    }

    // Función para limpiar el formulario
    fun clearForm() {
        foodName = ""
        calories = ""
        protein = ""
        selectedIcon = "🥛"
        selectedColor = FoodColor.Blue
        showErrors = false
        foodNameError = ""
        caloriesError = ""
        proteinError = ""
    }

    // Función para manejar el registro del alimento
    fun handleFoodRegistration() {
        coroutineScope.launch {
            isLoading = true
            try {
                delay(1000)

                val newFood = Food(
                    id = System.currentTimeMillis(),
                    name = foodName.trim(),
                    calories = calories.toDouble(),
                    totalFats = 0.0, // Valores por defecto
                    protein = protein.toDouble(),
                    carbohydrates = 0.0, // Valores por defecto
                    sugar = 0.0, // Valores por defecto
                    icon = selectedIcon,
                    color = selectedColor
                )

                onFoodRegistered(newFood)
                snackbarHostState.showSnackbar("¡Alimento registrado exitosamente!")
                clearForm()

            } catch (e: Exception) {
                snackbarHostState.showSnackbar("Error al registrar el alimento. Intente nuevamente.")
            } finally {
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Agregar Alimento")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClicked,
                        enabled = !isLoading
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Campo Nombre del Alimento
            Column {
                Text(
                    text = "Nombre del Alimento",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = foodName,
                    onValueChange = {
                        foodName = it
                        if (showErrors) {
                            foodNameError = validateFoodName(it.trim())
                        }
                    },
                    placeholder = { Text("00") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    isError = showErrors && foodNameError.isNotEmpty(),
                    supportingText = if (showErrors && foodNameError.isNotEmpty()) {
                        { Text(foodNameError) }
                    } else null,
                    singleLine = true
                )
            }

            // Campo Calorías
            Column {
                Text(
                    text = "Calorías (por 100g)",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = calories,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                            calories = newValue
                            if (showErrors) {
                                caloriesError = validateNumericField(newValue, "Calorías")
                            }
                        }
                    },
                    placeholder = { Text("Ej: 120") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    isError = showErrors && caloriesError.isNotEmpty(),
                    supportingText = if (showErrors && caloriesError.isNotEmpty()) {
                        { Text(caloriesError) }
                    } else null,
                    singleLine = true
                )
            }

            // Campo Proteínas
            Column {
                Text(
                    text = "Proteínas (gramos)",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = protein,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                            protein = newValue
                            if (showErrors) {
                                proteinError = validateNumericField(newValue, "Proteínas")
                            }
                        }
                    },
                    placeholder = { Text("Ej: 4.5") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    isError = showErrors && proteinError.isNotEmpty(),
                    supportingText = if (showErrors && proteinError.isNotEmpty()) {
                        { Text(proteinError) }
                    } else null,
                    singleLine = true
                )
            }

            // Sección de Iconos
            Column {
                Text(
                    text = "Icono",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(12.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    maxItemsInEachRow = 6
                ) {
                    availableIcons.forEach { icon ->
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (selectedIcon == icon) Color(0xFFE3F2FD)
                                    else Color(0xFFF5F5F5)
                                )
                                .border(
                                    width = if (selectedIcon == icon) 2.dp else 0.dp,
                                    color = if (selectedIcon == icon) Color(0xFF1976D2) else Color.Transparent,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { selectedIcon = icon },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = icon,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
            }

            // Sección de Colores de Fondo
            Column {
                Text(
                    text = "Color de Fondo",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(12.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    maxItemsInEachRow = 4
                ) {
                    availableColors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .width(80.dp)
                                .height(40.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(color.color)
                                .border(
                                    width = if (selectedColor == color) 3.dp else 0.dp,
                                    color = if (selectedColor == color) Color(0xFF333333) else Color.Transparent,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .clickable { selectedColor = color },
                            contentAlignment = Alignment.Center
                        ) {
                            if (selectedColor == color) {
                                Text(
                                    text = color.name,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            } else {
                                Text(
                                    text = color.name,
                                    fontSize = 10.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            // Vista Previa
            Column {
                Text(
                    text = "Vista Previa",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier
                        .width(120.dp)
                        .height(140.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = selectedColor.color
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = selectedIcon,
                            fontSize = 32.sp
                        )

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = if (foodName.isNotEmpty()) foodName else "00",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                maxLines = 1
                            )
                            Text(
                                text = "0 cal/100g",
                                fontSize = 10.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                            Text(
                                text = "P: 0g",
                                fontSize = 10.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onBackClicked,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    enabled = !isLoading
                ) {
                    Text("Cancelar")
                }

                Button(
                    onClick = {
                        if (isFormValid) {
                            handleFoodRegistration()
                        } else {
                            showErrors = true
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Por favor, complete todos los campos correctamente")
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    enabled = isFormValid && !isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1976D2)
                    )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp,
                            color = Color.White
                        )
                    } else {
                        Text(text = "Agregar")
                    }
                }
            }
        }
    }
}

// Enum para los colores de fondo
// Enum para los colores de fondo - CORREGIDO
enum class FoodColor(val color: Color, val displayName: String) {
    Blue(Color(0xFF2196F3), "Azul"),
    Yellow(Color(0xFFFFC107), "Amarillo"),
    Orange(Color(0xFFFF9800), "Naranja"),
    Green(Color(0xFF4CAF50), "Verde"),
    Purple(Color(0xFF9C27B0), "Morado"),
    Pink(Color(0xFFE91E63), "Rosa"),
    Amber(Color(0xFFFF8F00), "Ámbar") // Cambiado a un color ámbar diferente
}


// Data class actualizada para representar un alimento
data class Food(
    val id: Long,
    val name: String,
    val calories: Double,
    val totalFats: Double,
    val protein: Double,
    val carbohydrates: Double,
    val sugar: Double,
    val icon: String = "🥛",
    val color: FoodColor = FoodColor.Blue
) {
    // Función para formatear los valores nutricionales
    fun getFormattedNutrition(): String {
        return "P: ${String.format("%.1f", protein)}g • " +
                "C: ${String.format("%.1f", carbohydrates)}g • " +
                "G: ${String.format("%.1f", totalFats)}g"
    }

    // Función para obtener las calorías formateadas
    fun getFormattedCalories(): String {
        return "${String.format("%.0f", calories)} kcal"
    }
}