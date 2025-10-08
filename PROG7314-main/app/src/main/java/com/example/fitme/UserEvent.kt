package com.example.fitme

sealed interface UserEvent {
    object createUser : UserEvent
    data class setUsername(val username: String) : UserEvent
    data class setEmail(val email: String) : UserEvent
    data class setHeight(val height: Double) : UserEvent
    data class setWeight(val weight: Double) : UserEvent
    data class setDob(val dob: String) : UserEvent
    data class setPhone(val phone: String) : UserEvent
    data class setPassword(val password: String) : UserEvent
    data class deleteUser(val user: User) : UserEvent
}