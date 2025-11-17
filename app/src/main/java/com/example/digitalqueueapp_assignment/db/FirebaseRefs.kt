package com.example.digitalqueueapp_assignment.db

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.CollectionReference

object FirebaseRefs {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    val usersCollection: CollectionReference = firestore.collection("users")
    val tokensCollection: CollectionReference = firestore.collection("tokens")
}
