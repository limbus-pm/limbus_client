// FoodViewModel.kt
package com.example.limbus_client.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FoodViewModel : ViewModel() {

    private val _foodList = MutableStateFlow<List<Food>>(
        listOf(
            Food(1, "Huevos", 155.0, 11.0, 13.0, 1.1, 1.1),
            Food(2, "Avena", 389.0, 6.9, 16.9, 66.3, 0.0),
            Food(3, "Plátano", 89.0, 0.3, 1.1, 23.0, 12.2),
            Food(4, "Yogur griego", 59.0, 0.4, 10.0, 3.6, 3.6),
            Food(5, "Pan integral", 247.0, 4.2, 13.0, 41.0, 4.2)
        )
    )
    val foodList: StateFlow<List<Food>> = _foodList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _selectedFood = MutableStateFlow<Food?>(null)
    val selectedFood: StateFlow<Food?> = _selectedFood.asStateFlow()

    // Función para buscar alimentos por nombre
    fun searchFood(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val filteredList = if (query.isEmpty()) {
                    // Mostrar lista completa si no hay búsqueda
                    listOf(
                        Food(1, "Huevos", 155.0, 11.0, 13.0, 1.1, 1.1),
                        Food(2, "Avena", 389.0, 6.9, 16.9, 66.3, 0.0),
                        Food(3, "Plátano", 89.0, 0.3, 1.1, 23.0, 12.2),
                        Food(4, "Yogur griego", 59.0, 0.4, 10.0, 3.6, 3.6),
                        Food(5, "Pan integral", 247.0, 4.2, 13.0, 41.0, 4.2)
                    )
                } else {
                    // Filtrar por nombre que contenga la búsqueda
                    _foodList.value.filter {
                        it.name.lowercase().contains(query.lowercase())
                    }
                }
                _foodList.value = filteredList
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Error al buscar alimentos: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Función para seleccionar un alimento
    fun selectFood(food: Food) {
        _selectedFood.value = food
    }

    // Función para limpiar la selección
    fun clearSelection() {
        _selectedFood.value = null
    }

    // Función para agregar un nuevo alimento
    fun addFood(food: Food) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val currentList = _foodList.value.toMutableList()
                val newFood = food.copy(id = currentList.maxByOrNull { it.id }?.id?.plus(1) ?: 1)
                currentList.add(newFood)
                _foodList.value = currentList
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Error al agregar alimento: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Función para eliminar un alimento
    fun removeFood(foodId: Int) {
        viewModelScope.launch {
            try {
                val currentList = _foodList.value.toMutableList()
                currentList.removeAll { it.id == foodId }
                _foodList.value = currentList
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Error al eliminar alimento: ${e.message}"
            }
        }
    }

    // Función para limpiar mensajes de error
    fun clearError() {
        _errorMessage.value = null
    }

    // Función para obtener alimento por ID
    fun getFoodById(id: Int): Food? {
        return _foodList.value.find { it.id == id }
    }

    // Función para cargar datos desde una fuente externa (simulada)
    fun loadFoodsFromApi() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Simular llamada a API
                kotlinx.coroutines.delay(1000) // Simular tiempo de carga

                // Aquí normalmente harías la llamada real a tu API
                // val response = foodRepository.getFoods()
                // _foodList.value = response

                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar datos: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

// Data class para el modelo Food (si no está definida en otro lugar)
data class Food(
    val id: Int,
    val name: String,
    val calories: Double,
    val fat: Double,
    val protein: Double,
    val carbs: Double,
    val sugar: Double
)