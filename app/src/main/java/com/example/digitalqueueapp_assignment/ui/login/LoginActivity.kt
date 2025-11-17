package com.example.digitalqueueapp_assignment.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.digitalqueueapp_assignment.R
import com.example.digitalqueueapp_assignment.db.FirebaseRefs
import com.example.digitalqueueapp_assignment.ui.dashboard.UserDashboardActivity
import com.example.digitalqueueapp_assignment.ui.dashboard.AdminDashboardActivity
import com.example.digitalqueueapp_assignment.ui.login.RegisterActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEt = findViewById<EditText>(R.id.etEmail)
        val passEt = findViewById<EditText>(R.id.etPassword)
        val loginBtn = findViewById<Button>(R.id.btnLogin)
        val registerLink = findViewById<TextView>(R.id.tvRegister)

        loginBtn.setOnClickListener {
            val email = emailEt.text.toString().trim()
            val pass = passEt.text.toString().trim()
            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            FirebaseRefs.auth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener { authRes ->
                    val uid = authRes.user?.uid ?: ""
                    // fetch role
                    FirebaseRefs.usersCollection.document(uid).get()
                        .addOnSuccessListener { doc ->
                            val role = doc?.getString("role") ?: "user"
                            if (role == "admin") {
                                startActivity(Intent(this, AdminDashboardActivity::class.java))
                            } else {
                                startActivity(Intent(this, UserDashboardActivity::class.java))
                            }
                            finish()
                        }.addOnFailureListener {
                            Toast.makeText(this, "Login succeeded but role fetch failed", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, UserDashboardActivity::class.java))
                            finish()
                        }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Login failed: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
        }

        registerLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
