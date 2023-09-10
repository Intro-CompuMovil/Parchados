package com.loschimbitas.parchados.activities.learning

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loschimbitas.parchados.databinding.ActivityCreateClassBinding

class CreateClass : AppCompatActivity() {

    private lateinit var binding: ActivityCreateClassBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateClassBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}