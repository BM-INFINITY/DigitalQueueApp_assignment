package com.example.digitalqueueapp_assignment.ui.queue

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.digitalqueueapp_assignment.R
import com.example.digitalqueueapp_assignment.db.FirebaseRefs
import com.google.firebase.firestore.Query
import java.util.*

class AdminQueueActivity : AppCompatActivity() {

    private lateinit var btnNext: Button
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_queue)

        btnNext = findViewById(R.id.btnNext)
        listView = findViewById(R.id.lvQueue)

        // listen for waiting tokens
        FirebaseRefs.tokensCollection.whereEqualTo("status", "waiting")
            .orderBy("createdAt", Query.Direction.ASCENDING)
            .addSnapshotListener { snap, _ ->
                val list = ArrayList<Map<String, String>>()
                snap?.forEach { doc ->
                    val tokenString = doc.getString("tokenString") ?: ""
                    val userId = doc.getString("userId") ?: ""
                    val map = mapOf("token" to tokenString, "uid" to userId, "id" to doc.id)
                    list.add(map)
                }
                val adapter = SimpleAdapter(
                    this,
                    list,
                    android.R.layout.simple_list_item_2,
                    arrayOf("token", "uid"),
                    intArrayOf(android.R.id.text1, android.R.id.text2)
                )
                listView.adapter = adapter
            }

        btnNext.setOnClickListener {
            // pick first waiting token and mark as serving
            FirebaseRefs.tokensCollection.whereEqualTo("status", "waiting")
                .orderBy("createdAt", Query.Direction.ASCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener { snap ->
                    if (!snap.isEmpty) {
                        val doc = snap.documents[0]
                        FirebaseRefs.tokensCollection.document(doc.id)
                            .update(mapOf("status" to "serving"))
                    }
                }
        }
    }
}
