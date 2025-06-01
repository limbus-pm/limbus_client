// ========================================
// 3. MainActivity.kt - Navegación actualizada
// ========================================
package com.example.limbus_client2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.limbus_client.presentation.ui.feature.auth.Formulario_1
import com.example.limbus_client.presentation.ui.feature.auth.Formulario_2
import com.example.limbus_client.presentation.ui.feature.auth.Formulario_3
import com.example.limbus_client.presentation.ui.feature.auth.Formulario_4
import com.example.limbus_client.presentation.ui.feature.auth.Formulario_5
import com.example.limbus_client.presentation.ui.feature.auth.LoginScreen
import com.example.limbus_client.presentation.ui.feature.auth.Presentacion_1
import com.example.limbus_client.presentation.ui.feature.auth.Presentacion_2
import com.example.limbus_client.presentation.ui.feature.auth.Presentacion_3
import com.example.limbus_client.presentation.ui.feature.auth.Presentacion_4
import com.example.limbus_client.presentation.ui.feature.auth.RegistrationCompletedScreen
import com.example.limbus_client.presentation.ui.feature.auth.WelcomeScreen
import com.example.limbus_client.presentation.ui.feature.dashboard.AddFoodScreen
import com.example.limbus_client.presentation.ui.feature.dashboard.FoodDiaryScreen
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
                    // Usar un state hoisting para controlar la navegación
                    val currentScreenState = remember { mutableStateOf("welcome") }

                    // Para pasar datos entre pantallas
                    val selectedMealType = remember { mutableStateOf("Desayuno") }

                    when (currentScreenState.value) {
                        "welcome" -> {
                            WelcomeScreen(
                                onStartClicked = {
                                    currentScreenState.value = "presentacion1"
                                    println("Navegando a presentacion1")
                                },
                                onLoginClicked = {
                                    currentScreenState.value = "login"
                                    println("Navegando a login")
                                }
                            )
                        }
                        "presentacion1" -> {
                            Presentacion_1(
                                onStartClicked = {
                                    currentScreenState.value = "presentacion2"
                                    println("Navegando a presentacion2")
                                },
                                onLoginClicked = {
                                    currentScreenState.value = "main"
                                    println("Saltar a pantalla principal")
                                }
                            )
                        }
                        "presentacion2" -> {
                            Presentacion_2(
                                onNextClicked = {
                                    currentScreenState.value = "presentacion3"
                                    println("Navegando a presentacion3")
                                },
                                onSkipClicked = {
                                    currentScreenState.value = "main"
                                    println("Saltar a pantalla principal")
                                }
                            )
                        }
                        "presentacion3" -> {
                            Presentacion_3(
                                onFinishClicked = {
                                    currentScreenState.value = "presentacion4"
                                    println("Navegando a presentacion4")
                                },
                                onSkipClicked = {
                                    currentScreenState.value = "main"
                                    println("Saltar a pantalla principal")
                                }
                            )
                        }
                        "presentacion4" -> {
                            Presentacion_4(
                                onFinishClicked = {
                                    currentScreenState.value = "login"
                                    println("Navegando a login")
                                },
                                onSkipClicked = {
                                    currentScreenState.value = "main"
                                    println("Saltar a pantalla principal")
                                }
                            )
                        }
                        "login" -> {
                            // Crear una instancia del ViewModel para la pantalla de login
                            val loginViewModel: LoginViewModel = viewModel()

                            LoginScreen(
                                viewModel = loginViewModel,
                                onLoginSuccess = {
                                    // Cuando el login es exitoso, ir al formulario de registro
                                    currentScreenState.value = "formulario1"
                                    println("Login exitoso, navegando a formulario de registro")
                                },
                                onNavigateToRegister = {
                                    // El botón de registro lleva al formulario
                                    currentScreenState.value = "formulario1"
                                    println("Navegando a formulario de registro")
                                },
                                onGoogleLoginClicked = { googleToken ->
                                    // Manejar login con Google
                                    println("Login con Google iniciado con token: $googleToken")
                                    // Por ahora, ir al formulario como si fuera login exitoso
                                    currentScreenState.value = "formulario1"
                                }
                            )
                        }
                        "formulario1" -> {
                            Formulario_1(
                                onContinueClicked = {
                                    currentScreenState.value = "formulario2"
                                    println("Navegando a formulario 2")
                                },
                                onBackClicked = {
                                    currentScreenState.value = "login"
                                    println("Volviendo a la pantalla de login")
                                },
                                onLoginClicked = {
                                    currentScreenState.value = "login"
                                    println("Redirigiendo a login")
                                }
                            )
                        }
                        "formulario2" -> {
                            Formulario_2(
                                onContinueClicked = {
                                    currentScreenState.value = "formulario3"
                                    println("Navegando a formulario 3")
                                },
                                onBackClicked = {
                                    currentScreenState.value = "formulario1"
                                    println("Volviendo al formulario 1")
                                },
                                onLoginClicked = {
                                    currentScreenState.value = "login"
                                    println("Redirigiendo a login")
                                }
                            )
                        }
                        "formulario3" -> {
                            Formulario_3(
                                onFinishClicked = {
                                    currentScreenState.value = "formulario4"
                                    println("Navegando a formulario 4")
                                },
                                onBackClicked = {
                                    currentScreenState.value = "formulario2"
                                    println("Volviendo al formulario 2")
                                },
                                onLoginClicked = {
                                    currentScreenState.value = "login"
                                    println("Redirigiendo a login")
                                }
                            )
                        }
                        "formulario4" -> {
                            Formulario_4(
                                onFinishClicked = {
                                    currentScreenState.value = "formulario5"
                                    println("Navegando a formulario 5")
                                },
                                onBackClicked = {
                                    currentScreenState.value = "formulario3"
                                    println("Volviendo al formulario 3")
                                },
                                onLoginClicked = {
                                    currentScreenState.value = "login"
                                    println("Redirigiendo a login")
                                }
                            )
                        }
                        "formulario5" -> {
                            Formulario_5(
                                onCompleteRegistrationClicked = {
                                    currentScreenState.value = "registration_completed"
                                    println("Registro completado, navegando a pantalla de confirmación")
                                },
                                onBackClicked = {
                                    currentScreenState.value = "formulario4"
                                    println("Volviendo al formulario 4")
                                }
                            )
                        }
                        "registration_completed" -> {
                            RegistrationCompletedScreen(
                                onContinueClicked = {
                                    currentScreenState.value = "food_diary"
                                    println("Navegando a pantalla de diario de comidas")
                                }
                            )
                        }
                        "food_diary" -> {
                            FoodDiaryScreen(
                                onBackToMainClicked = {
                                    currentScreenState.value = "main"
                                    println("Volviendo a la pantalla principal")
                                },
                                onProfileClicked = {
                                    currentScreenState.value = "profile"
                                    println("Navegando al perfil del usuario")
                                },
                                onAddFoodClicked = {
                                    selectedMealType.value = "Desayuno"
                                    currentScreenState.value = "add_food"
                                    println("Navegando a añadir alimentos")
                                }
                            )
                        }
                        "register" -> {
                            // Compatibilidad: redirigir al formulario
                            currentScreenState.value = "formulario1"
                        }
                        "forgot_password" -> {
                            Text(
                                text = "Recuperación de Contraseña - Funcionalidad en desarrollo",
                                style = MaterialTheme.typography.headlineLarge,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        "main" -> {
                            // Redirigir al diario de comidas
                            currentScreenState.value = "food_diary"
                        }
                        "add_food" -> {
                            AddFoodScreen(
                                onBackClicked = {
                                    currentScreenState.value = "food_diary"
                                    println("Volviendo al diario de comidas")
                                },
                                onFoodSelected = { foodType ->
                                    println("Seleccionado tipo de comida: $foodType")
                                    currentScreenState.value = "food_diary"
                                }
                            )
                        }
                        "profile" -> {
                            Text(
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
