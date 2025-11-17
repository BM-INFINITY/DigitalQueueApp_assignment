package com.example.digitalqueueapp_assignment.model

import com.google.firebase.Timestamp
import java.io.Serializable

data class Token(
    val tokenId: String = "",
    val tokenNumber: Long = 0L,
    val tokenString: String = "",
    val userId: String = "",
    val status: String = "waiting", // waiting|serving|done|skipped|cancelled
    val createdAt: Timestamp? = null
) : Serializable
