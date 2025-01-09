package com.example.fooddelivery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fooddelivery.SignUpUserActivity
import com.example.fooddelivery.databinding.ActivityLoginUserBinding
import com.example.fooddelivery.databinding.ActivityStartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginUserBinding
    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goSignUpUser.setOnClickListener{
            val intent = Intent(this@LoginUserActivity, SignUpUserActivity::class.java)
            startActivity(intent)
        }

        binding.button3.setOnClickListener {
//            val intent = Intent(this@LoginUserActivity, LocationActivity::class.java)
//            startActivity(intent)
//            finish()
            val email = binding.signInEmail.text.toString().trim()
            val password = binding.signInPassword.text.toString().trim()
            signIn(email, password)
        }
    }
    private fun signIn(email: String, password: String) {
        // Проверяем, что все поля заполнены
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    Toast.makeText(this, "Вы успешно вошли!", Toast.LENGTH_SHORT).show()
                    // Перейти на другой экран или выполнить другие действия
                    val intent = Intent(this@LoginUserActivity, LocationActivity::class.java)
                    startActivity(intent)
                    finish()
                    Log.d("MyLog", "Sign in successful")

                } else {
                    Log.d("MyLog", "Sign in failed")
                    Toast.makeText(this, "Пользователь не найден! Проверьте введённые данные", Toast.LENGTH_SHORT).show()
                }
            }
    }
}