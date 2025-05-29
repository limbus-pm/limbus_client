package com.example.limbus_client.presentation.ui.feature.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlin.math.roundToInt

// Modelos de datos
data class FoodOption(
    val id: String,
    val name: String,
    val calories: Int,
    val imageUrl: String? = null,
    val protein: Double = 0.0,
    val carbs: Double = 0.0,
    val fat: Double = 0.0,
    val fiber: Double = 0.0,
    val mealTypes: List<String> = emptyList()
)

data class Recipe(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val readyInMinutes: Int,
    val servings: Int,
    val calories: Int,
    val rating: Double = 0.0,
    val isFavorite: Boolean = false,
    val mealTypes: List<String> = emptyList()
)

data class CookbookRecipe(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val calories: Int,
    val prepTime: String,
    val difficulty: String,
    val isPersonal: Boolean = true,
    val mealTypes: List<String> = emptyList()
)

data class PortionType(
    val name: String,
    val grams: Double,
    val description: String
)

data class FoodRegistration(
    val id: String = System.currentTimeMillis().toString(),
    val food: FoodOption,
    val quantity: Double,
    val portionType: PortionType,
    val mealType: String,
    val totalCalories: Int,
    val totalProtein: Double,
    val totalCarbs: Double,
    val totalFat: Double,
    val totalFiber: Double
)

// Constantes
val mealTypes = listOf("Desayuno", "Almuerzo", "Cena", "Snack")

enum class FoodTab { COOKBOOK, RECIPES, FOODS }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodScreen(
    onBackClicked: () -> Unit,
    onFoodSelected: (String) -> Unit,
    onFoodRegistered: (FoodRegistration) -> Unit = {},
    initialRegisteredFoods: List<FoodRegistration> = emptyList() // Para recibir alimentos previamente guardados
) {
    // Estados
    var selectedMealTypeIndex by remember { mutableStateOf(0) }
    var isFabExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(FoodTab.FOODS) }

    // Lista de alimentos registrados (inicializada con datos previos)
    var registeredFoods by remember { mutableStateOf(initialRegisteredFoods) }

    // Estado para controlar la expansión de la sección de alimentos añadidos
    var isAddedFoodsExpanded by remember { mutableStateOf(true) }

    // Estados para modales
    var showRegistrationModal by remember { mutableStateOf(false) }
    var selectedFoodForRegistration by remember { mutableStateOf<FoodOption?>(null) }
    var showCustomRecipeModal by remember { mutableStateOf(false) }
    var showSaveConfirmationModal by remember { mutableStateOf(false) }

    // Función para agregar alimento a la lista
    val addFoodToList = { registration: FoodRegistration ->
        registeredFoods = registeredFoods + registration
        onFoodRegistered(registration)
    }

    // Función para eliminar alimento de la lista
    val removeFoodFromList = { foodId: String ->
        registeredFoods = registeredFoods.filter { it.id != foodId }
    }

    // Función para guardar todos los alimentos
    val saveAllFoods = {
        if (registeredFoods.isNotEmpty()) {
            showSaveConfirmationModal = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(text = mealTypes[selectedMealTypeIndex])
                        if (registeredFoods.isNotEmpty()) {
                            Text(
                                text = "${registeredFoods.size} alimento(s) preparado(s)",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    // Botón de guardar visible solo si hay alimentos
                    if (registeredFoods.isNotEmpty()) {
                        Button(
                            onClick = saveAllFoods,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary,
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text("Guardar Todo")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
                AnimatedVisibility(
                    visible = isFabExpanded,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        MealTypeFab(type = "Crear receta personalizada", onClick = {
                            showCustomRecipeModal = true
                            isFabExpanded = false
                        })
                        MealTypeFab(type = "Buscar alimento común", onClick = {
                            onFoodSelected("Alimento común")
                            isFabExpanded = false
                        })
                        MealTypeFab(type = "Escanear código de barras", onClick = {
                            onFoodSelected("Escanear código")
                            isFabExpanded = false
                        })
                        MealTypeFab(type = "Importar de API", onClick = {
                            onFoodSelected("Importar API")
                            isFabExpanded = false
                        })
                    }
                }
                FloatingActionButton(
                    onClick = { isFabExpanded = !isFabExpanded },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = if (isFabExpanded) Icons.Default.Close else Icons.Default.Add,
                        contentDescription = if (isFabExpanded) "Cerrar opciones" else "Añadir alimento"
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Selector de tipo de comida
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                mealTypes.forEachIndexed { index, type ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = mealTypes.size
                        ),
                        onClick = { selectedMealTypeIndex = index },
                        selected = index == selectedMealTypeIndex
                    ) {
                        Text(type)
                    }
                }
            }

            // *** NUEVA SECCIÓN: Alimentos Añadidos (siempre visible y más prominente) ***
            AddedFoodsSection(
                registeredFoods = registeredFoods,
                mealType = mealTypes[selectedMealTypeIndex],
                isExpanded = isAddedFoodsExpanded,
                onToggleExpanded = { isAddedFoodsExpanded = !isAddedFoodsExpanded },
                onRemoveFood = removeFoodFromList,
                onSaveAll = saveAllFoods
            )

            // Barra de búsqueda
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { isSearchActive = false },
                active = isSearchActive,
                onActiveChange = { isSearchActive = it },
                placeholder = {
                    Text(when(selectedTab) {
                        FoodTab.COOKBOOK -> "Buscar en tu libro de cocina"
                        FoodTab.RECIPES -> "Buscar recetas online"
                        FoodTab.FOODS -> "Buscar alimentos y productos"
                    })
                },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") }
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text(
                        "Resultados para '$searchQuery'",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    when(selectedTab) {
                        FoodTab.COOKBOOK -> Text("Buscando en recetas guardadas...", style = MaterialTheme.typography.bodyMedium)
                        FoodTab.RECIPES -> Text("Buscando en bases de datos de recetas...", style = MaterialTheme.typography.bodyMedium)
                        FoodTab.FOODS -> Text("Buscando alimentos y productos...", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            // Pestañas
            TabRow(selectedTabIndex = selectedTab.ordinal) {
                Tab(
                    selected = selectedTab == FoodTab.COOKBOOK,
                    onClick = { selectedTab = FoodTab.COOKBOOK },
                    text = { Text("LIBRO DE COCINA") }
                )
                Tab(
                    selected = selectedTab == FoodTab.RECIPES,
                    onClick = { selectedTab = FoodTab.RECIPES },
                    text = { Text("RECETAS") }
                )
                Tab(
                    selected = selectedTab == FoodTab.FOODS,
                    onClick = { selectedTab = FoodTab.FOODS },
                    text = { Text("ALIMENTOS") }
                )
            }

            // Contenido según la pestaña seleccionada
            when(selectedTab) {
                FoodTab.COOKBOOK -> CookbookContent(mealTypes[selectedMealTypeIndex])
                FoodTab.RECIPES -> RecipesContent(mealTypes[selectedMealTypeIndex])
                FoodTab.FOODS -> FoodsContent(
                    mealType = mealTypes[selectedMealTypeIndex],
                    onFoodClick = { food ->
                        selectedFoodForRegistration = food
                        showRegistrationModal = true
                    }
                )
            }
        }
    }

    // Modal de registro de alimento
    if (showRegistrationModal && selectedFoodForRegistration != null) {
        AddFoodRegistrationModal(
            food = selectedFoodForRegistration!!,
            mealType = mealTypes[selectedMealTypeIndex],
            onDismiss = {
                showRegistrationModal = false
                selectedFoodForRegistration = null
            },
            onConfirm = { registration ->
                addFoodToList(registration)
                showRegistrationModal = false
                selectedFoodForRegistration = null
            }
        )
    }

    // Modal de receta personalizada
    if (showCustomRecipeModal) {
        CreateCustomRecipeModal(
            onDismiss = { showCustomRecipeModal = false },
            onNavigateToRegisterFood = {
                showCustomRecipeModal = false
                onFoodSelected("RegisterFoodScreen")
            }
        )
    }

    // Modal de confirmación de guardado
    if (showSaveConfirmationModal) {
        SaveConfirmationModal(
            foodsCount = registeredFoods.size,
            mealType = mealTypes[selectedMealTypeIndex],
            onDismiss = { showSaveConfirmationModal = false },
            onConfirm = {
                // Aquí guardas todos los alimentos en la base de datos/estado global
                // registeredFoods.forEach { food -> saveToDatabase(food) }
                showSaveConfirmationModal = false
                onBackClicked() // Regresar a la pantalla anterior
            }
        )
    }
}

// *** NUEVA SECCIÓN: Componente dedicado para Alimentos Añadidos ***
@Composable
fun AddedFoodsSection(
    registeredFoods: List<FoodRegistration>,
    mealType: String,
    isExpanded: Boolean,
    onToggleExpanded: () -> Unit,
    onRemoveFood: (String) -> Unit,
    onSaveAll: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (registeredFoods.isNotEmpty()) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (registeredFoods.isNotEmpty()) 4.dp else 1.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header de la sección
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { if (registeredFoods.isNotEmpty()) onToggleExpanded() },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = if (registeredFoods.isNotEmpty()) Icons.Default.CheckCircle else Icons.Default.Add,
                        contentDescription = null,
                        tint = if (registeredFoods.isNotEmpty()) Color(0xFF4CAF50) else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(24.dp)
                    )

                    Column {
                        Text(
                            text = if (registeredFoods.isNotEmpty()) {
                                "Alimentos para $mealType"
                            } else {
                                "Sin alimentos añadidos"
                            },
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = if (registeredFoods.isNotEmpty()) {
                                MaterialTheme.colorScheme.onPrimaryContainer
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )

                        if (registeredFoods.isNotEmpty()) {
                            Text(
                                text = "${registeredFoods.size} alimento(s) • ${registeredFoods.sumOf { it.totalCalories }} cal total",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            )
                        } else {
                            Text(
                                text = "Usa el botón + para añadir alimentos",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        }
                    }
                }

                if (registeredFoods.isNotEmpty()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Botón de guardar todo (pequeño)
                        Button(
                            onClick = onSaveAll,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50),
                                contentColor = Color.White
                            ),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text(
                                "Guardar Todo",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }

                        // Botón de expandir/colapsar
                        IconButton(
                            onClick = onToggleExpanded,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = if (isExpanded) "Colapsar" else "Expandir",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

            // Contenido expandible
            AnimatedVisibility(
                visible = isExpanded && registeredFoods.isNotEmpty()
            ) {
                Column(
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    // Resumen nutricional total
                    val totalCalories = registeredFoods.sumOf { it.totalCalories }
                    val totalProtein = registeredFoods.sumOf { it.totalProtein }
                    val totalCarbs = registeredFoods.sumOf { it.totalCarbs }
                    val totalFat = registeredFoods.sumOf { it.totalFat }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            NutritionSummary("Calorías", "${totalCalories}")
                            NutritionSummary("Proteínas", "${String.format("%.1f", totalProtein)}g")
                            NutritionSummary("Carbos", "${String.format("%.1f", totalCarbs)}g")
                            NutritionSummary("Grasas", "${String.format("%.1f", totalFat)}g")
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Lista de alimentos individuales
                    registeredFoods.forEach { registration ->
                        RegisteredFoodItem(
                            registration = registration,
                            onRemove = { onRemoveFood(registration.id) }
                        )
                        if (registration != registeredFoods.last()) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

// *** NUEVO: Modal de confirmación de guardado ***
@Composable
fun SaveConfirmationModal(
    foodsCount: Int,
    mealType: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icono de confirmación
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(64.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "¿Guardar alimentos?",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Se guardarán $foodsCount alimento(s) en tu $mealType",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancelar")
                    }
                    Button(
                        onClick = onConfirm,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50)
                        )
                    ) {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}

// Resto de los componentes (sin cambios significativos)
@Composable
fun CreateCustomRecipeModal(
    onDismiss: () -> Unit,
    onNavigateToRegisterFood: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Crear Receta Personalizada",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "👨‍🍳",
                    style = MaterialTheme.typography.displayLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Añadir Alimento",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancelar")
                    }
                    Button(
                        onClick = onNavigateToRegisterFood,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Continuar")
                    }
                }
            }
        }
    }
}

@Composable
fun NutritionSummary(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun RegisteredFoodItem(
    registration: FoodRegistration,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = getFoodEmoji(registration.food.name),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = registration.food.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                // Continuación del archivo AddFoodScreen.kt

                Text(
                    text = "${String.format("%.1f", registration.quantity)} ${registration.portionType.name.lowercase()} (${String.format("%.0f", registration.quantity * registration.portionType.grams)}g)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${registration.totalCalories} cal • P: ${String.format("%.1f", registration.totalProtein)}g • C: ${String.format("%.1f", registration.totalCarbs)}g • G: ${String.format("%.1f", registration.totalFat)}g",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(
                onClick = onRemove,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun MealTypeFab(
    type: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
        shadowElevation = 4.dp
    ) {
        Text(
            text = type,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
fun CookbookContent(mealType: String) {
    // Datos de ejemplo para el libro de cocina personal
    val cookbookRecipes = remember {
        listOf(
            CookbookRecipe(
                id = "1",
                name = "Ensalada de Quinoa y Vegetales",
                imageUrl = null,
                calories = 320,
                prepTime = "15 min",
                difficulty = "Fácil",
                isPersonal = true,
                mealTypes = listOf("Almuerzo", "Cena")
            ),
            CookbookRecipe(
                id = "2",
                name = "Smoothie Proteico Verde",
                imageUrl = null,
                calories = 180,
                prepTime = "5 min",
                difficulty = "Muy Fácil",
                isPersonal = true,
                mealTypes = listOf("Desayuno", "Snack")
            ),
            CookbookRecipe(
                id = "3",
                name = "Pollo al Horno con Verduras",
                imageUrl = null,
                calories = 450,
                prepTime = "45 min",
                difficulty = "Intermedio",
                isPersonal = true,
                mealTypes = listOf("Almuerzo", "Cena")
            )
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Tus Recetas Guardadas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items(cookbookRecipes.filter { mealType in it.mealTypes }) { recipe ->
            CookbookRecipeCard(recipe = recipe)
        }

        if (cookbookRecipes.filter { mealType in it.mealTypes }.isEmpty()) {
            item {
                EmptyStateCard(
                    title = "Sin recetas para $mealType",
                    description = "Aún no tienes recetas guardadas para esta comida",
                    emoji = "📝"
                )
            }
        }
    }
}

@Composable
fun RecipesContent(mealType: String) {
    // Datos de ejemplo para recetas online
    val onlineRecipes = remember {
        listOf(
            Recipe(
                id = "1",
                title = "Tazón Mediterráneo con Quinoa",
                imageUrl = null,
                readyInMinutes = 25,
                servings = 2,
                calories = 380,
                rating = 4.7,
                isFavorite = false,
                mealTypes = listOf("Almuerzo", "Cena")
            ),
            Recipe(
                id = "2",
                title = "Avena con Frutas y Nueces",
                imageUrl = null,
                readyInMinutes = 10,
                servings = 1,
                calories = 290,
                rating = 4.5,
                isFavorite = true,
                mealTypes = listOf("Desayuno")
            ),
            Recipe(
                id = "3",
                title = "Salmón Glaseado con Brócoli",
                imageUrl = null,
                readyInMinutes = 30,
                servings = 2,
                calories = 420,
                rating = 4.8,
                isFavorite = false,
                mealTypes = listOf("Almuerzo", "Cena")
            )
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Recetas Populares",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items(onlineRecipes.filter { mealType in it.mealTypes }) { recipe ->
            RecipeCard(recipe = recipe)
        }

        if (onlineRecipes.filter { mealType in it.mealTypes }.isEmpty()) {
            item {
                EmptyStateCard(
                    title = "Sin recetas para $mealType",
                    description = "No encontramos recetas populares para esta comida",
                    emoji = "🔍"
                )
            }
        }
    }
}

@Composable
fun FoodsContent(
    mealType: String,
    onFoodClick: (FoodOption) -> Unit
) {
    // Datos de ejemplo para alimentos
    val foodOptions = remember {
        listOf(
            FoodOption(
                id = "1",
                name = "Pechuga de Pollo",
                calories = 165,
                protein = 31.0,
                carbs = 0.0,
                fat = 3.6,
                fiber = 0.0,
                mealTypes = listOf("Almuerzo", "Cena")
            ),
            FoodOption(
                id = "2",
                name = "Avena Integral",
                calories = 389,
                protein = 16.9,
                carbs = 66.3,
                fat = 6.9,
                fiber = 10.6,
                mealTypes = listOf("Desayuno")
            ),
            FoodOption(
                id = "3",
                name = "Quinoa Cocida",
                calories = 120,
                protein = 4.4,
                carbs = 22.0,
                fat = 1.9,
                fiber = 2.8,
                mealTypes = listOf("Almuerzo", "Cena", "Desayuno")
            ),
            FoodOption(
                id = "4",
                name = "Huevo Entero",
                calories = 155,
                protein = 13.0,
                carbs = 1.1,
                fat = 11.0,
                fiber = 0.0,
                mealTypes = listOf("Desayuno", "Almuerzo", "Cena", "Snack")
            ),
            FoodOption(
                id = "5",
                name = "Plátano Mediano",
                calories = 105,
                protein = 1.3,
                carbs = 27.0,
                fat = 0.4,
                fiber = 3.1,
                mealTypes = listOf("Desayuno", "Snack")
            ),
            FoodOption(
                id = "6",
                name = "Almendras",
                calories = 579,
                protein = 21.2,
                carbs = 21.6,
                fat = 49.9,
                fiber = 12.5,
                mealTypes = listOf("Snack", "Desayuno")
            )
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(foodOptions.filter { mealType in it.mealTypes || it.mealTypes.isEmpty() }) { food ->
            FoodCard(
                food = food,
                onClick = { onFoodClick(food) }
            )
        }
    }
}

@Composable
fun CookbookRecipeCard(recipe: CookbookRecipe) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle click */ },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder para imagen
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🍽️",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = recipe.prepTime,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = recipe.difficulty,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = "${recipe.calories} calorías",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            IconButton(onClick = { /* Toggle favorite */ }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorito",
                    tint = if (recipe.isPersonal) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle click */ },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder para imagen
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🥗",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = "${recipe.readyInMinutes} min",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                            tint = Color(0xFFFFD700)
                        )
                        Text(
                            text = String.format("%.1f", recipe.rating),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }
                }
                Text(
                    text = "${recipe.calories} cal • ${recipe.servings} porción(es)",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            IconButton(onClick = { /* Toggle favorite */ }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorito",
                    tint = if (recipe.isFavorite) Color(0xFFE91E63)
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun FoodCard(
    food: FoodOption,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = getFoodEmoji(food.name),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = food.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = "${food.calories} cal/100g",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "P: ${String.format("%.1f", food.protein)}g",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun EmptyStateCard(
    title: String,
    description: String,
    emoji: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = emoji,
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun AddFoodRegistrationModal(
    food: FoodOption,
    mealType: String,
    onDismiss: () -> Unit,
    onConfirm: (FoodRegistration) -> Unit
) {
    var quantity by remember { mutableDoubleStateOf(1.0) }
    var selectedPortionIndex by remember { mutableIntStateOf(0) }

    val portionTypes = remember {
        listOf(
            PortionType("Gramos", 1.0, "Peso en gramos"),
            PortionType("Porción", 100.0, "Porción estándar (100g)"),
            PortionType("Taza", 150.0, "Una taza (~150g)"),
            PortionType("Cucharada", 15.0, "Una cucharada (~15g)")
        )
    }

    val selectedPortion = portionTypes[selectedPortionIndex]
    val totalGrams = quantity * selectedPortion.grams
    val multiplier = totalGrams / 100.0

    val totalCalories = (food.calories * multiplier).roundToInt()
    val totalProtein = food.protein * multiplier
    val totalCarbs = food.carbs * multiplier
    val totalFat = food.fat * multiplier
    val totalFiber = food.fiber * multiplier

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Añadir Alimento",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Food info
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = getFoodEmoji(food.name),
                        style = MaterialTheme.typography.displaySmall
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = food.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Para $mealType",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Quantity input
                Text(
                    text = "Cantidad",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = { if (quantity > 0.1) quantity -= 0.1 },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Icon(
                            Icons.Default.Remove,
                            contentDescription = "Reducir",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    OutlinedTextField(
                        value = String.format("%.1f", quantity),
                        onValueChange = { newValue ->
                            newValue.toDoubleOrNull()?.let { value ->
                                if (value >= 0) quantity = value
                            }
                        },
                        modifier = Modifier.weight(1f),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            textAlign = TextAlign.Center
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true
                    )

                    IconButton(
                        onClick = { quantity += 0.1 },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Aumentar",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Portion type selector
                Text(
                    text = "Tipo de porción",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyColumn(
                    modifier = Modifier.height(120.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    itemsIndexed(portionTypes) { index, portion ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedPortionIndex = index },
                            colors = CardDefaults.cardColors(
                                containerColor = if (index == selectedPortionIndex) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant
                                }
                            ),
                            border = if (index == selectedPortionIndex) {
                                BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                            } else null
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = portion.name,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = portion.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                if (index == selectedPortionIndex) {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = "Seleccionado",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Nutritional summary
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Resumen nutricional",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "${String.format("%.0f", totalGrams)}g total",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            NutritionItem("Calorías", "${totalCalories}")
                            NutritionItem("Proteínas", "${String.format("%.1f", totalProtein)}g")
                            NutritionItem("Carbohidratos", "${String.format("%.1f", totalCarbs)}g")
                            NutritionItem("Grasas", "${String.format("%.1f", totalFat)}g")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancelar")
                    }
                    Button(
                        onClick = {
                            val registration = FoodRegistration(
                                food = food,
                                quantity = quantity,
                                portionType = selectedPortion,
                                mealType = mealType,
                                totalCalories = totalCalories,
                                totalProtein = totalProtein,
                                totalCarbs = totalCarbs,
                                totalFat = totalFat,
                                totalFiber = totalFiber
                            )
                            onConfirm(registration)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Añadir")
                    }
                }
            }
        }
    }
}

@Composable
fun NutritionItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )
    }
}

// Función auxiliar para obtener emojis según el nombre del alimento
fun getFoodEmoji(foodName: String): String {
    return when {
        foodName.contains("pollo", ignoreCase = true) -> "🍗"
        foodName.contains("huevo", ignoreCase = true) -> "🥚"
        foodName.contains("avena", ignoreCase = true) -> "🥣"
        foodName.contains("quinoa", ignoreCase = true) -> "🌾"
        foodName.contains("plátano", ignoreCase = true) -> "🍌"
        foodName.contains("almendra", ignoreCase = true) -> "🥜"
        foodName.contains("ensalada", ignoreCase = true) -> "🥗"
        foodName.contains("smoothie", ignoreCase = true) -> "🥤"
        foodName.contains("salmón", ignoreCase = true) -> "🐟"
        foodName.contains("verdura", ignoreCase = true) -> "🥦"
        foodName.contains("fruta", ignoreCase = true) -> "🍎"
        else -> "🍽️"
    }
}