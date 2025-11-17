package com.example.digitalqueueapp_assignment.ui.queue

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.digitalqueueapp_assignment.R
import com.example.digitalqueueapp_assignment.db.FirebaseRefs
import com.google.firebase.firestore.Query

class TokenStatusActivity : AppCompatActivity() {
    private lateinit var tvStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_token_status)
        tvStatus = findViewById(R.id.tvStatus)

        val uid = FirebaseRefs.auth.currentUser?.uid ?: return

        // listen for user's latest token
        FirebaseRefs.tokensCollection.whereEqualTo("userId", uid)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(1)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null && !snapshot.isEmpty) {
                    val doc = snapshot.documents[0]
                    val tokenString = doc.getString("tokenString") ?: ""
                    val status = doc.getString("status") ?: ""
                    // compute position (count waiting tokens with smaller createdAt)
                    FirebaseRefs.tokensCollection.whereEqualTo("status", "waiting")
                        .orderBy("createdAt", Query.Direction.ASCENDING)
                        .get()
                        .addOnSuccessListener { waitingSnap ->
                            val position = waitingSnap.documents.indexOfFirst { it.id == doc.id } + 1
                            val waitingCount = waitingSnap.size()
                            tvStatus.text = "Token: $tokenString\nStatus: $status\nPosition: $position / $waitingCount"
                        }
                } else {
                    tvStatus.text = "No token found"
                }
            }
    }
}
