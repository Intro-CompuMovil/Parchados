package com.loschimbitas.parchados.activities.parchar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loschimbitas.parchados.databinding.ActivityJoinAParcheBinding

class JoinAParche : AppCompatActivity() {

    private lateinit var binding: ActivityJoinAParcheBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinAParcheBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}