package com.example.pc2epjv

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()
        // Verificar si el usuario ya está autenticado
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = etEmail.text.trim().toString()
            val password = etPassword.text.trim().toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                if (email.isEmpty()) {
                    etEmail.error = "Ingrese su eMail"
                }
                if (password.isEmpty()) {
                    etPassword.error = "Ingrese su Password"
                }
            }
        }
    }
    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Iniciar la siguiente actividad si el inicio de sesión es exitoso
                    val user: FirebaseUser? = auth.currentUser
                    Toast.makeText(this, "Bienvenido ${user?.email}", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, NavigationMainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Mostrar error en caso de fallo
                    Toast.makeText(this, "Error de autenticación: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}