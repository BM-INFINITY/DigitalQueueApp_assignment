package com.example.digitalqueueapp_assignment.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.digitalqueueapp_assignment.R
import com.example.digitalqueueapp_assignment.db.FirebaseRefs
import com.example.digitalqueueapp_assignment.ui.queue.GetTokenActivity

class UserDashboardActivity : AppCompatActivity() {

    private lateinit var tvWelcome: TextView
    private lateinit var btnGetToken: Button
    private lateinit var btnMyTokenStatus: Button
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_dashboard)

        tvWelcome = findViewById(R.id.tvWelcome)
        btnGetToken = findViewById(R.id.btnGetToken)
        btnMyTokenStatus = findViewById(R.id.btnMyTokenStatus)
        btnLogout = findViewById(R.id.btnLogout)

        val uid = FirebaseRefs.auth.currentUser?.uid ?: ""
        FirebaseRefs.usersCollection.document(uid).get()
            .addOnSuccessListener { doc ->
                val name = doc?.getString("name") ?: "User"
                tvWelcome.text = "Welcome, $name"
            }

        btnGetToken.setOnClickListener {
            startActivity(Intent(this, GetTokenActivity::class.java))
        }

        btnMyTokenStatus.setOnClickListener {
            startActivity(Intent(this, com.example.digitalqueueapp_assignment.ui.queue.TokenStatusActivity::class.java))
        }

        btnLogout.setOnClickListener {
            FirebaseRefs.auth.signOut()
            startActivity(Intent(this, com.example.digitalqueueapp_assignment.ui.login.LoginActivity::class.java))
            finish()
        }
    }
}
