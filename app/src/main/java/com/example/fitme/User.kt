package com.example.fitme

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val userId : String = UUID.randomUUID().toString(),
    val username : String,
    val email : String,
    val height : Double,
    val weight : Double,
    val dob : String,
    val phone : String,
    val password : String
)
