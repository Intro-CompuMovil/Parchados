package com.loschimbitas.parchados.activities.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.loschimbitas.parchados.databinding.ActivityParcharBinding

class Parchar : AppCompatActivity() {

    private lateinit var binding: ActivityParcharBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParcharBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLearn.setOnClickListener {
            startActivity(Intent(this, Learn::class.java))
            finish()
        }
    }
}