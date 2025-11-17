package com.example.digitalqueueapp_assignment.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.digitalqueueapp_assignment.db.FirebaseRefs
import com.example.digitalqueueapp_assignment.ui.login.LoginActivity
import com.example.digitalqueueapp_assignment.ui.dashboard.UserDashboardActivity
import com.example.digitalqueueapp_assignment.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // optional layout if you have activity_splash.xml
        setContentView(R.layout.activity_splash)

        // quick check: if user is logged in navigate to appropriate dashboard
        val user = FirebaseRefs.auth.currentUser
        window.decorView.postDelayed({
            if (user == null) {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                // check role from Firestore
                FirebaseRefs.usersCollection.document(user.uid).get()
                    .addOnSuccessListener { doc ->
                        val role = doc?.getString("role") ?: "user"
                        if (role == "admin") {
                            startActivity(Intent(this, com.example.digitalqueueapp_assignment.ui.dashboard.AdminDashboardActivity::class.java))
                        } else {
                            startActivity(Intent(this, UserDashboardActivity::class.java))
                        }
                    }
                    .addOnFailureListener {
                        // fallback
                        startActivity(Intent(this, UserDashboardActivity::class.java))
                    }
            }
            finish()
        }, 1000)
    }
}
