package com.loschimbitas.parchados.activities.learning

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loschimbitas.parchados.databinding.ActivityJoinAClassBinding

class JoinAClass : AppCompatActivity() {

    private lateinit var binding: ActivityJoinAClassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinAClassBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}