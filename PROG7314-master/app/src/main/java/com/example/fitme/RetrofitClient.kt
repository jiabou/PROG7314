package com.example.fitme

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofit = Retrofit.Builder()
    .baseUrl("https://your-render-app.onrender.com")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val api = retrofit.create(FoodApiService::class.java)