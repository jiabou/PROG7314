package com.example.fitme

sealed class ProgressEvent {
    data class AddFoodIntake(val foodName: String, val calories: Int) : ProgressEvent()
    object LoadTodayProgress : ProgressEvent()
    object LoadProgressData : ProgressEvent()
}