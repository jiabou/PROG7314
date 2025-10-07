package com.example.fitme

import retrofit2.Call
import retrofit2.http.GET

interface FoodApiService {
    @GET("/api/foods")
    fun getAllFoods(): Call<FoodListResponse>
}

// Response wrapper to match your backend JSON structure
data class FoodListResponse(
    val success: Boolean,
    val data: List<Food>
)