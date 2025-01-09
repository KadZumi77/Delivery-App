package com.example.fooddelivery

import User
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fooddelivery.databinding.ActivitySignUpUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SignUpUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpUserBinding
    private val auth = Firebase.auth
    private lateinit var database: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fs = Firebase.firestore

//        // Инициализация Firebase Auth и Database

        binding.goLoginUserPage.setOnClickListener {
            val intent = Intent(this@SignUpUserActivity, LoginUserActivity::class.java)
            startActivity(intent)
        }

        binding.button3.setOnClickListener {
            val email = binding.signInEmail.text.toString().trim()
            val password = binding.signInPassword.text.toString().trim()
            signUp(auth, email, password)
        }
    }

    private fun signUp(auth: FirebaseAuth, email: String, password: String) {
        val name = binding.signUpUserName.text.toString().trim()
        // Проверяем, что все поля заполнены
        if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_SHORT).show()
                    // Перейти на другой экран или выполнить другие действия
                    val intent = Intent(this@SignUpUserActivity, LocationActivity::class.java)
                    startActivity(intent)
                    finish()
                    Log.d("MyLog", "Sign up successful")

                } else {
                    Log.d("MyLog", "Sign up failed")
                    Toast.makeText(this, "Ошибка регистрации: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}