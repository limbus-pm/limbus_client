package com.example.limbus_client.presentation.ui.feature.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun FoodNavigationContainer() {
    // Estado para controlar qué pantalla mostrar
    var currentScreen by remember { mutableStateOf(FoodScreen.LIST) }

    // Estado compartido para la lista de alimentos
    var foodList by remember {
        mutableStateOf(
            listOf(
                Food(1, "Huevos", 155.0, 11.0, 13.0, 1.1, 1.1),
                Food(2, "Avena", 389.0, 6.9, 16.9, 66.3, 0.0),
                Food(3, "Plátano", 89.0, 0.3, 1.1, 23.0, 12.2),
                Food(4, "Yogur griego", 59.0, 0.4, 10.0, 3.6, 3.6),
                Food(5, "Pan integral", 247.0, 4.2, 13.0, 41.0, 4.2)
            )
        )
    }

    when (currentScreen) {
        FoodScreen.LIST -> {
            FoodListScreenWithState(
                foodList = foodList,
                onAddFoodClicked = {
                    currentScreen = FoodScreen.REGISTER
                }
            )
        }
        FoodScreen.REGISTER -> {
            RegisterFoodScreen(
                onBackClicked = {
                    currentScreen = FoodScreen.LIST
                },
                onFoodRegistered = { newFood ->
                    foodList = foodList + newFood
                    currentScreen = FoodScreen.LIST
                }
            )
        }
    }
}

enum class FoodScreen {
    LIST,
    REGISTER
}