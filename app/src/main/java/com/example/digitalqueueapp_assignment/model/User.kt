package com.example.digitalqueueapp_assignment.model

import java.io.Serializable

data class User(
    val uid: String = "",
    val email: String = "",
    val name: String = "",
    val role: String = "user" // "admin" or "user"
) : Serializable
