package com.loschimbitas.parchados.activities.active

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.loschimbitas.parchados.R
import com.loschimbitas.parchados.databinding.ActivityInProgressBinding

class InProgress : AppCompatActivity() {

    private lateinit var binding: ActivityInProgressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialize()
    }

    private fun initialize() {
        initListeners()
    }

    private fun initListeners() {

    }
}