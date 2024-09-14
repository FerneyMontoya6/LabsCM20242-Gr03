package com.example.labscm20242_gr03.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.labscm20242_gr03.services.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CityViewModel : ViewModel() {
    private val _cities = MutableStateFlow<List<City>>(emptyList())
    val cities: StateFlow<List<City>> get() = _cities

    init {
        fetchCities()
    }

    private fun fetchCities() {
        viewModelScope.launch {
            try {
                val citiesList = RetrofitInstance.api.getCities()
                _cities.value = citiesList
            } catch (e: Exception) {
                // Manejar errores aquí
            }
        }
    }
}