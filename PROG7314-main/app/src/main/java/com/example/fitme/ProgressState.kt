package com.example.fitme

data class ProgressState(
    val todayIntake: List<FoodIntake> = emptyList(),
    val totalCalories: Int = 0,
    val weeklyCalories: List<Int> = emptyList(),
    val days: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)