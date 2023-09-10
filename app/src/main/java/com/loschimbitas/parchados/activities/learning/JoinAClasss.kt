package com.loschimbitas.parchados.activities.learning

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loschimbitas.parchados.databinding.ActivityJoinAClasssBinding

class JoinAClasss : AppCompatActivity() {

    private lateinit var binding: ActivityJoinAClasssBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinAClasssBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}