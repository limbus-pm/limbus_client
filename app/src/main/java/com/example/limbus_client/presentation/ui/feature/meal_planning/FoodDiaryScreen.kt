package com.example.limbus_client.presentation.ui.feature.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.util.*

@Composable
fun FoodDiaryScreen(
    onBackToMainClicked: () -> Unit,
    onProfileClicked: () -> Unit,
    onAddFoodClicked: () -> Unit
) {
    // Estado del calendario
    val calendarState = rememberMutableCalendarState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(painterResource(id = android.R.drawable.ic_menu_search), contentDescription = "Buscar") },
                    label = { Text("Busca") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(painterResource(id = android.R.drawable.ic_menu_view), contentDescription = "Menú") },
                    label = { Text("Menú") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(painterResource(id = android.R.drawable.ic_menu_edit), contentDescription = "Diario") },
                    label = { Text("Diario") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(painterResource(id = android.R.drawable.ic_menu_help), contentDescription = "Aprende") },
                    label = { Text("Aprende") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onProfileClicked,
                    icon = { Icon(painterResource(id = android.R.drawable.ic_menu_myplaces), contentDescription = "Perfil") },
                    label = { Text("Perfil") }
                )
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Header con título y navegación de calendario
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Diario",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    CalendarNavigationHeader(
                        calendarState = calendarState.toCalendarState(),
                        onDateNavigate = { forward ->
                            calendarState.updateState(
                                currentDate = CalendarManager.navigateDate(
                                    calendarState.currentDate,
                                    calendarState.viewType,
                                    forward
                                )
                            )
                        },
                        onViewTypeToggle = {
                            calendarState.updateState(
                                viewType = CalendarManager.getNextViewType(calendarState.viewType)
                            )
                        },
                        onDateClick = {
                            calendarState.updateState(showDatePicker = true)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tarjeta de calorías
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Círculo de calorías
                            Box(
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFEEEEFF)),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.Bottom
                                    ) {
                                        Text(
                                            text = "0",
                                            fontSize = 32.sp,
                                            fontWeight = FontWeight.Bold
                                        )

                                        Spacer(modifier = Modifier.width(4.dp))

                                        Text(
                                            text = "kcal",
                                            fontSize = 16.sp,
                                            color = Color.Gray
                                        )
                                    }

                                    Text(
                                        text = "consumidas",
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Información de macronutrientes
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                MacroNutrientInfo(name = "Carbo", value = "0/250 gr")
                                MacroNutrientInfo(name = "Proteínas", value = "0/188 gr")
                                MacroNutrientInfo(name = "Grasas", value = "0/83 gr")
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = "Información",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(16.dp)
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Text(
                                    text = "¡Pásate a ekilu+ para ver tu balance calórico y macros!",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }

                        // Botón añadir en la esquina superior derecha
                        FloatingActionButton(
                            onClick = { /* Añadir alimento */ },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(40.dp),
                            containerColor = Color(0xFFFF7043)
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Añadir",
                                tint = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Lista de comidas del día
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Desayuno
                    MealCard(
                        mealName = "Desayuno",
                        onAddClicked = onAddFoodClicked
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Almuerzo
                    MealCard(
                        mealName = "Almuerzo",
                        onAddClicked = onAddFoodClicked
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Comida
                    MealCard(
                        mealName = "Comida",
                        onAddClicked = onAddFoodClicked
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Merienda (Snack)
                    MealCard(
                        mealName = "Merienda",
                        onAddClicked = onAddFoodClicked
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Cena
                    MealCard(
                        mealName = "Cena",
                        onAddClicked = onAddFoodClicked
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Snacks adicionales
                    MealCard(
                        mealName = "Snacks",
                        onAddClicked = onAddFoodClicked
                    )

                    // Espacio adicional en la parte inferior para asegurar que todo sea visible
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    // Date Picker Dialog
    if (calendarState.showDatePicker) {
        DatePickerDialog(
            selectedDate = calendarState.currentDate,
            onDateSelected = { selectedDate ->
                calendarState.updateState(
                    currentDate = selectedDate,
                    selectedDate = selectedDate,
                    showDatePicker = false
                )
            },
            onDismiss = {
                calendarState.updateState(showDatePicker = false)
            }
        )
    }
}

@Composable
fun CalendarNavigationHeader(
    calendarState: CalendarState,
    onDateNavigate: (Boolean) -> Unit,
    onViewTypeToggle: () -> Unit,
    onDateClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.End
    ) {
        // Selector de vista (DIARIO, SEMANAL, MENSUAL)
        Text(
            text = CalendarManager.getViewTypeLabel(calendarState.viewType),
            fontSize = 12.sp,
            color = Color(0xFF673AB7),
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .clickable { onViewTypeToggle() }
                .padding(vertical = 4.dp)
        )

        // Navegación de fechas
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onDateNavigate(false) }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Anterior",
                    tint = Color(0xFF673AB7)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { onDateClick() }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = CalendarManager.getFormattedDateText(
                        calendarState.currentDate,
                        calendarState.viewType
                    ),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (CalendarManager.isToday(calendarState.currentDate) &&
                        calendarState.viewType == CalendarViewType.DAILY) {
                        Color(0xFF673AB7)
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )

                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    Icons.Default.DateRange,
                    contentDescription = "Seleccionar fecha",
                    tint = Color(0xFF673AB7),
                    modifier = Modifier.size(16.dp)
                )
            }

            IconButton(onClick = { onDateNavigate(true) }) {
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = "Siguiente",
                    tint = Color(0xFF673AB7)
                )
            }
        }
    }
}

@Composable
fun DatePickerDialog(
    selectedDate: Date,
    onDateSelected: (Date) -> Unit,
    onDismiss: () -> Unit
) {
    var currentMonth by remember {
        val cal = Calendar.getInstance()
        cal.time = selectedDate
        mutableStateOf(cal.get(Calendar.MONTH))
    }
    var currentYear by remember {
        val cal = Calendar.getInstance()
        cal.time = selectedDate
        mutableStateOf(cal.get(Calendar.YEAR))
    }
    var tempSelectedDate by remember { mutableStateOf(selectedDate) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Seleccionar fecha",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )

                    IconButton(onClick = onDismiss) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Cerrar"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Fecha seleccionada
                val dateText = remember(tempSelectedDate) {
                    val cal = Calendar.getInstance(Locale("es", "ES"))
                    cal.time = tempSelectedDate
                    val dayNames = arrayOf("Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb")
                    val monthNames = arrayOf("Ene", "Feb", "Mar", "Abr", "May", "Jun",
                        "Jul", "Ago", "Sep", "Oct", "Nov", "Dic")
                    "${dayNames[cal.get(Calendar.DAY_OF_WEEK) - 1]}, ${monthNames[cal.get(Calendar.MONTH)]} ${cal.get(Calendar.DAY_OF_MONTH)}"
                }

                Text(
                    text = dateText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF673AB7)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Navegación del mes
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        if (currentMonth == 0) {
                            currentMonth = 11
                            currentYear--
                        } else {
                            currentMonth--
                        }
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Mes anterior")
                    }

                    val monthName = remember(currentMonth, currentYear) {
                        val monthNames = arrayOf("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
                        "${monthNames[currentMonth]} $currentYear"
                    }

                    Text(
                        text = monthName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                    IconButton(onClick = {
                        if (currentMonth == 11) {
                            currentMonth = 0
                            currentYear++
                        } else {
                            currentMonth++
                        }
                    }) {
                        Icon(Icons.Default.ArrowForward, contentDescription = "Mes siguiente")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Días de la semana
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf("D", "L", "M", "X", "J", "V", "S").forEach { day ->
                        Text(
                            text = day,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Calendario de días del mes
                val daysInMonth = remember(currentMonth, currentYear) {
                    val cal = Calendar.getInstance()
                    cal.set(currentYear, currentMonth, 1)
                    val firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1
                    val daysInCurrentMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

                    // Crear lista de días incluyendo espacios vacíos al inicio
                    val days = mutableListOf<Int?>()
                    repeat(firstDayOfWeek) { days.add(null) }
                    repeat(daysInCurrentMonth) { days.add(it + 1) }
                    days
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier.height(240.dp),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items(daysInMonth) { day ->
                        if (day != null) {
                            val isSelected = remember(tempSelectedDate, day, currentMonth, currentYear) {
                                val selectedCal = Calendar.getInstance()
                                selectedCal.time = tempSelectedDate
                                selectedCal.get(Calendar.DAY_OF_MONTH) == day &&
                                        selectedCal.get(Calendar.MONTH) == currentMonth &&
                                        selectedCal.get(Calendar.YEAR) == currentYear
                            }

                            val isToday = remember(day, currentMonth, currentYear) {
                                val today = Calendar.getInstance()
                                today.get(Calendar.DAY_OF_MONTH) == day &&
                                        today.get(Calendar.MONTH) == currentMonth &&
                                        today.get(Calendar.YEAR) == currentYear
                            }

                            Box(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .padding(2.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            isSelected -> Color(0xFF673AB7)
                                            isToday -> Color(0xFF673AB7).copy(alpha = 0.2f)
                                            else -> Color.Transparent
                                        }
                                    )
                                    .border(
                                        width = if (isToday && !isSelected) 1.dp else 0.dp,
                                        color = Color(0xFF673AB7),
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        val newDate = Calendar.getInstance()
                                        newDate.set(currentYear, currentMonth, day)
                                        tempSelectedDate = newDate.time
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = day.toString(),
                                    fontSize = 14.sp,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                    fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.aspectRatio(1f))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botones de acción
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(
                            text = "Cancelar",
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { onDateSelected(tempSelectedDate) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF673AB7)
                        )
                    ) {
                        Text(
                            text = "Aceptar",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MacroNutrientInfo(
    name: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            fontSize = 12.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun MealCard(
    mealName: String,
    onAddClicked: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = mealName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "0 kcal",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            OutlinedButton(
                onClick = onAddClicked,
                modifier = Modifier.size(width = 80.dp, height = 36.dp),
                contentPadding = PaddingValues(horizontal = 8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF673AB7)
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    Color(0xFF673AB7)
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Añadir",
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "Añadir",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}