package com.example.digitalqueueapp_assignment.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.digitalqueueapp_assignment.R
import com.example.digitalqueueapp_assignment.db.FirebaseRefs
import com.example.digitalqueueapp_assignment.model.User
import com.example.digitalqueueapp_assignment.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val nameEt = findViewById<EditText>(R.id.etName)
        val emailEt = findViewById<EditText>(R.id.etEmail)
        val passEt = findViewById<EditText>(R.id.etPassword)
        val registerBtn = findViewById<Button>(R.id.btnRegister)

        registerBtn.setOnClickListener {
            val name = nameEt.text.toString().trim()
            val email = emailEt.text.toString().trim()
            val pass = passEt.text.toString().trim()
            if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            FirebaseRefs.auth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener { authRes ->
                    val uid = authRes.user?.uid ?: ""
                    val user = User(uid = uid, email = email, name = name, role = "user")
                    FirebaseRefs.usersCollection.document(uid).set(user)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }.addOnFailureListener {
                            Toast.makeText(this, "User save failed: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
                        }
                }.addOnFailureListener {
                    Toast.makeText(this, "Register failed: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
