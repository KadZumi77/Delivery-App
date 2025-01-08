package com.example.fooddelivery

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fooddelivery.databinding.ActivityDetailsViewBinding

class DetailsViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backStack.setOnClickListener {
            onBackPressed()
        }

        binding.placeMyOrder.setOnClickListener {
            val bottomFragment = SuccessPaymentFragment()
            bottomFragment.show(supportFragmentManager, "Test")
        }

        val totalPrice = intent.getStringExtra("totalPrice")
        binding.price.text = totalPrice
    }
}