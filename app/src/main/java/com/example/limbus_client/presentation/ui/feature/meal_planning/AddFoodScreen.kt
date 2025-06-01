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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

// Modelo de datos para las categorías de comida
data class FoodCategory(
    val name: String,
    val icon: Int // Esto sería R.drawable.icon_name
)

// Modelo de datos para las opciones de comida
data class FoodOption(
    val name: String,
    val calories: Int,
    val imageUrl: String? = null
)

// Lista de categorías de comidas (mockup)
val mealTypes = listOf(
    "Desayuno",
    "Almuerzo",
    "Cena",
    "Snack"
)

// Lista de APIs de recetas que podrías integrar
val recipeApis = listOf(
    "Spoonacular API - Amplia base de datos de recetas y nutrición",
    "Edamam Recipe API - Gran cantidad de recetas con información nutricional",
    "TheMealDB - API gratuita con muchas recetas internacionales",
    "Tasty API - Recetas de BuzzFeed con videos instructivos",
    "Recipe Puppy API - API simple para buscar recetas por ingredientes"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodScreen(
    onBackClicked: () -> Unit,
    onFoodSelected: (String) -> Unit
) {
    // Estado para controlar qué tipo de comida está seleccionada
    var selectedMealTypeIndex by remember { mutableStateOf(0) }

    // Estado para controlar si el FAB de opciones está expandido
    var isFabExpanded by remember { mutableStateOf(false) }

    // Estado para la búsqueda
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = mealTypes[selectedMealTypeIndex]) },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
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
                // Menú expandible con opciones
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
                        MealTypeFab(type = "Receta personalizada", onClick = {
                            onFoodSelected("Receta personalizada")
                            isFabExpanded = false
                        })
                        MealTypeFab(type = "Alimento común", onClick = {
                            onFoodSelected("Alimento común")
                            isFabExpanded = false
                        })
                        MealTypeFab(type = "Escanear código", onClick = {
                            onFoodSelected("Escanear código")
                            isFabExpanded = false
                        })
                    }
                }
                // FAB principal
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
            // Selector de tipo de comida (desayuno, almuerzo, etc.)
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
                placeholder = { Text("Buscar recetas o alimentos") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") }
            ) {
                // Contenido de sugerencias de búsqueda
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text(
                        "Resultados de búsqueda para '$searchQuery'",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            // Pestañas para filtrar (LIBRO DE COCINA, RECETAS, ALIMENTOS)
            TabRow(selectedTabIndex = 0) {
                Tab(
                    selected = true,
                    onClick = { /* cambiar a LIBRO DE COCINA */ },
                    text = { Text("LIBRO DE COCINA") }
                )
                Tab(
                    selected = false,
                    onClick = { /* cambiar a RECETAS */ },
                    text = { Text("RECETAS") }
                )
                Tab(
                    selected = false,
                    onClick = { /* cambiar a ALIMENTOS */ },
                    text = { Text("ALIMENTOS") }
                )
            }

            // Grid de recetas
            RecipeGrid(
                mealType = mealTypes[selectedMealTypeIndex]
            )
        }
    }
}

@Composable
fun MealTypeFab(type: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = type,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun RecipeGrid(mealType: String) {
    // Aquí podrías filtrar las recetas según el tipo de comida seleccionado
    val recetas = when (mealType) {
        "Desayuno" -> listOf(
            FoodOption("Magdalenas de Avena", 180),
            FoodOption("Bizcocho Avena/Zanahoria", 220),
            FoodOption("Crema de Champiñones", 120),
            FoodOption("Bizcocho de Manzana", 240),
            FoodOption("Pancakes de Avena", 210),
            FoodOption("Muffins de Plátano", 190)
        )
        "Almuerzo" -> listOf(
            FoodOption("Ensalada César", 320),
            FoodOption("Pollo a la plancha", 280),
            FoodOption("Sopa de verduras", 150),
            FoodOption("Wrap vegetal", 310)
        )
        "Cena" -> listOf(
            FoodOption("Salmón al horno", 350),
            FoodOption("Tortilla española", 280),
            FoodOption("Crema de calabaza", 180),
            FoodOption("Tofu salteado", 230)
        )
        else -> listOf(
            FoodOption("Yogur con frutos secos", 180),
            FoodOption("Fruta fresca", 90),
            FoodOption("Batido proteico", 210),
            FoodOption("Barritas energéticas", 150)
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(recetas) { receta ->
            RecipeCard(receta)
        }
    }
}

@Composable
fun RecipeCard(recipe: FoodOption) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clickable { /* Navegar a detalle de receta */ },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            // Aquí iría una imagen de la receta
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.LightGray)
            ) {
                // En un caso real, cargarías la imagen desde recipe.imageUrl
                Text(
                    text = "${recipe.calories} cal",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(4.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Text(
                text = recipe.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Esta función sería para mostrar un diálogo con las APIs de recetas disponibles
@Composable
fun RecipeApiDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "APIs de Recetas Recomendadas",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    recipeApis.forEach { api ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                        ) {
                            Text(
                                text = api,
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Estas APIs te permitirán buscar recetas, obtener información nutricional y más para tu aplicación.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}