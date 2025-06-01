package com.example.limbus_client.presentation.ui.feature.dashboard

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FoodDiaryScreen(
    onBackToMainClicked: () -> Unit,
    onProfileClicked: () -> Unit,
    onAddFoodClicked: () -> Unit
) {
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
                // Header con título y navegación
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

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { /* Día anterior */ }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Día anterior")
                        }

                        Text(
                            text = "21 de mayo",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )

                        IconButton(onClick = { /* Día siguiente */ }) {
                            Icon(Icons.Default.ArrowForward, contentDescription = "Día siguiente")
                        }
                    }
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
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .height(4.dp)
                .background(Color(0xFFEEEEEE))
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun MealCard(
    mealName: String,
    onAddClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(id = android.R.drawable.ic_menu_today),
                    contentDescription = null,
                    tint = Color(0xFF673AB7),
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = mealName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                    TextButton(
                        onClick = onAddClicked,
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "Añade comida",
                            color = Color(0xFF673AB7),
                            fontSize = 14.sp
                        )
                    }
                }
            }

            FloatingActionButton(
                onClick = onAddClicked,
                modifier = Modifier.size(32.dp),
                containerColor = Color(0xFF673AB7)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Añadir comida",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}