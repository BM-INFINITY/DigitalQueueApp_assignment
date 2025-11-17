package com.example.digitalqueueapp_assignment.ui.queue

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.digitalqueueapp_assignment.R
import com.example.digitalqueueapp_assignment.db.FirebaseRefs
import com.example.digitalqueueapp_assignment.model.Token
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import java.util.Locale

class GetTokenActivity : AppCompatActivity() {

    private lateinit var tvTokenInfo: TextView
    private lateinit var btnGetToken: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_token)

        tvTokenInfo = findViewById(R.id.tvTokenInfo)
        btnGetToken = findViewById(R.id.btnGetToken)

        btnGetToken.setOnClickListener {
            createToken()
        }

        refreshInfo()
    }

    private fun refreshInfo() {
        // show number of people in queue (waiting)
        FirebaseRefs.tokensCollection.whereEqualTo("status", "waiting")
            .orderBy("createdAt", Query.Direction.ASCENDING)
            .get().addOnSuccessListener { snap ->
                val waiting = snap.size()
                tvTokenInfo.text = "People waiting: $waiting"
            }
    }

    private fun createToken() {
        val uid = FirebaseRefs.auth.currentUser?.uid ?: return
        // compute next tokenNumber: get last tokenNumber
        FirebaseRefs.tokensCollection.orderBy("tokenNumber", Query.Direction.DESCENDING).limit(1)
            .get().addOnSuccessListener { snap ->
                val last = if (!snap.isEmpty) snap.documents[0].getLong("tokenNumber") ?: 0L else 0L
                val nextNum = last + 1
                val tokenString = String.format(Locale.getDefault(), "Q%03d", nextNum)
                val newDoc = FirebaseRefs.tokensCollection.document()
                val token = Token(
                    tokenId = newDoc.id,
                    tokenNumber = nextNum,
                    tokenString = tokenString,
                    userId = uid,
                    status = "waiting",
                    createdAt = Timestamp.now()
                )
                newDoc.set(token).addOnSuccessListener {
                    tvTokenInfo.text = "Your token: $tokenString\nPosition: calculating..."
                }.addOnFailureListener {
                    tvTokenInfo.text = "Token create failed: ${it.localizedMessage}"
                }
            }
    }
}
