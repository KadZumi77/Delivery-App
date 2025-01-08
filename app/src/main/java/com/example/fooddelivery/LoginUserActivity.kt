package com.example.fooddelivery

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fooddelivery.SignUpUserActivity
import com.example.fooddelivery.databinding.ActivityLoginUserBinding
import com.example.fooddelivery.databinding.ActivityStartBinding

class LoginUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goSignUpUser.setOnClickListener{
            val intent = Intent(this@LoginUserActivity, SignUpUserActivity::class.java)
            startActivity(intent)
        }

        binding.button3.setOnClickListener {
            val intent = Intent(this@LoginUserActivity, LocationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}