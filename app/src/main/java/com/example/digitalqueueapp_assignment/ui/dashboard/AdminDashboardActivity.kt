package com.example.digitalqueueapp_assignment.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.digitalqueueapp_assignment.R
import com.example.digitalqueueapp_assignment.db.FirebaseRefs
import com.example.digitalqueueapp_assignment.ui.queue.AdminQueueActivity

class AdminDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        findViewById<Button>(R.id.btnOpenQueue).setOnClickListener {
            startActivity(Intent(this, AdminQueueActivity::class.java))
        }

        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            FirebaseRefs.auth.signOut()
            startActivity(Intent(this, com.example.digitalqueueapp_assignment.ui.login.LoginActivity::class.java))
            finish()
        }
    }
}
