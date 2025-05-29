package com.example.limbus_client2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.limbus_client.presentation.ui.feature.auth.*
import com.example.limbus_client.presentation.ui.feature.dashboard.*
import com.example.limbus_client.presentation.viewmodel.LoginViewModel
import com.example.limbus_client2.ui.theme.Limbus_client2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Limbus_client2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val currentScreenState = remember { mutableStateOf("welcome") }
                    val selectedMealType = remember { mutableStateOf("Desayuno") }
                    val customFoods = remember { mutableStateListOf<Food>() }

                    when (currentScreenState.value) {
                        "welcome" -> WelcomeScreen(
                            onStartClicked = { currentScreenState.value = "presentacion1" },
                            onLoginClicked = { currentScreenState.value = "login" }
                        )
                        "presentacion1" -> Presentacion_1(
                            onStartClicked = { currentScreenState.value = "presentacion2" },
                            onLoginClicked = { currentScreenState.value = "main" }
                        )
                        "presentacion2" -> Presentacion_2(
                            onNextClicked = { currentScreenState.value = "presentacion3" },
                            onSkipClicked = { currentScreenState.value = "main" }
                        )
                        "presentacion3" -> Presentacion_3(
                            onFinishClicked = { currentScreenState.value = "presentacion4" },
                            onSkipClicked = { currentScreenState.value = "main" }
                        )
                        "presentacion4" -> Presentacion_4(
                            onFinishClicked = { currentScreenState.value = "login" },
                            onSkipClicked = { currentScreenState.value = "main" }
                        )
                        "login" -> {
                            val loginViewModel: LoginViewModel = viewModel()
                            LoginScreen(
                                viewModel = loginViewModel,
                                onLoginSuccess = { currentScreenState.value = "formulario1" },
                                onNavigateToRegister = { currentScreenState.value = "formulario1" },
                                onGoogleLoginClicked = {
                                    currentScreenState.value = "formulario1"
                                }
                            )
                        }
                        "formulario1" -> Formulario_1(
                            onContinueClicked = { currentScreenState.value = "formulario2" },
                            onBackClicked = { currentScreenState.value = "login" },
                            onLoginClicked = { currentScreenState.value = "login" }
                        )
                        "formulario2" -> Formulario_2(
                            onContinueClicked = { currentScreenState.value = "formulario3" },
                            onBackClicked = { currentScreenState.value = "formulario1" },
                            onLoginClicked = { currentScreenState.value = "login" }
                        )
                        "formulario3" -> Formulario_3(
                            onFinishClicked = { currentScreenState.value = "formulario4" },
                            onBackClicked = { currentScreenState.value = "formulario2" },
                            onLoginClicked = { currentScreenState.value = "login" }
                        )
                        "formulario4" -> Formulario_4(
                            onFinishClicked = { currentScreenState.value = "formulario5" },
                            onBackClicked = { currentScreenState.value = "formulario3" },
                            onLoginClicked = { currentScreenState.value = "login" }
                        )
                        "formulario5" -> Formulario_5(
                            onCompleteRegistrationClicked = {
                                currentScreenState.value = "registration_completed"
                            },
                            onBackClicked = { currentScreenState.value = "formulario4" }
                        )
                        "registration_completed" -> RegistrationCompletedScreen(
                            onContinueClicked = {
                                currentScreenState.value = "food_diary"
                            }
                        )
                        "food_diary" -> FoodDiaryScreen(
                            onBackToMainClicked = { currentScreenState.value = "main" },
                            onProfileClicked = { currentScreenState.value = "profile" },
                            onAddFoodClicked = {
                                selectedMealType.value = "Desayuno"
                                currentScreenState.value = "add_food"
                            }
                        )
                        "register" -> { currentScreenState.value = "formulario1" }
                        "forgot_password" -> {
                            androidx.compose.material3.Text(
                                text = "Recuperación de Contraseña - Funcionalidad en desarrollo",
                                style = MaterialTheme.typography.headlineLarge,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        "main" -> { currentScreenState.value = "food_diary" }
                        "add_food" -> AddFoodScreen(
                            onBackClicked = { currentScreenState.value = "food_diary" },
                            onFoodSelected = { type ->
                                if (type == "RegisterFoodScreen") {
                                    currentScreenState.value = "register_food"
                                } else {
                                    currentScreenState.value = "food_diary"
                                }
                            },
                            onFoodRegistered = { newFoodRegistration ->
                                // No se usa aquí porque AddFoodScreen registra directamente FoodRegistration
                            },
                            initialRegisteredFoods = customFoods.map { food ->
                                FoodRegistration(
                                    food = FoodOption(
                                        id = food.id.toString(),
                                        name = food.name,
                                        calories = food.calories.toInt(),
                                        protein = food.protein,
                                        carbs = food.carbohydrates,
                                        fat = food.totalFats,
                                        fiber = food.sugar,
                                        mealTypes = listOf("Desayuno", "Almuerzo", "Cena", "Snack")
                                    ),
                                    quantity = 1.0,
                                    portionType = PortionType("Porción", 100.0, "Porción estándar"),
                                    mealType = selectedMealType.value,
                                    totalCalories = food.calories.toInt(),
                                    totalProtein = food.protein,
                                    totalCarbs = food.carbohydrates,
                                    totalFat = food.totalFats,
                                    totalFiber = food.sugar
                                )
                            }
                        )
                        "register_food" -> RegisterFoodScreen(
                            onBackClicked = { currentScreenState.value = "add_food" },
                            onFoodRegistered = { newFood: Food ->
                                customFoods.add(newFood)
                                currentScreenState.value = "add_food"
                            }
                        )
                        "profile" -> {
                            androidx.compose.material3.Text(
                                text = "Perfil de Usuario - Funcionalidad en desarrollo",
                                style = MaterialTheme.typography.headlineLarge,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}
