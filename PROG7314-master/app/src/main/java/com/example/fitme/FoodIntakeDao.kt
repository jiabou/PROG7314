package com.example.fitme

import androidx.room.*

@Dao
interface FoodIntakeDao {
    @Insert
    suspend fun addIntake(food: FoodIntake)

    @Query("SELECT * FROM food_intake WHERE date = :date")
    suspend fun getTodayIntake(date: String): List<FoodIntake>

    @Query("SELECT SUM(calories) FROM food_intake WHERE date = :date")
    suspend fun getTotalCalories(date: String): Int?


    // Get total calories grouped by date for the past 7 days
    @Query("""
        SELECT date, SUM(calories) as totalCalories
        FROM food_intake
        WHERE date BETWEEN :startDate AND :endDate
        GROUP BY date
        ORDER BY date ASC
    """)
    suspend fun getCaloriesForLast7Days(startDate: String, endDate: String): List<CaloriesByDate>
}
