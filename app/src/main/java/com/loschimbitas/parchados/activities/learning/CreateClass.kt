package com.loschimbitas.parchados.activities.learning

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loschimbitas.parchados.activities.selectplacemap.SelectPlace
import com.loschimbitas.parchados.databinding.ActivityCreateClassBinding

class CreateClass : AppCompatActivity() {

    private lateinit var binding: ActivityCreateClassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateClassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()
    }

    private fun initialize() {
        initListeners()
    }

    private fun initListeners() {
        setUpCreateClassListener()
    }

    private fun setUpCreateClassListener() {
        binding.buttonSelectPlaceOnMap.setOnClickListener {
            val intent = Intent(this, SelectPlace::class.java)
            intent.putExtra("root", "CreateClass")
            startActivity(intent)
            finish()
        }
    }
}