package com.example.labscm20242_gr03.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class City(
    val id: Int,
    val name: String,
    val description: String?,
    val surface: Double?,
    val population: Int?,
    val postalCode: String?,
    val departmentId: Int?,
    val department: String?,
    val touristAttractions: String?,
    val presidents: String?,
    val indigenousReservations: String?,
    val airports: String?,
    val radios: String?
)

interface ApiService {
    @GET("api/v1/City")
    suspend fun getCities(): List<City>
}

object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api-colombia.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
