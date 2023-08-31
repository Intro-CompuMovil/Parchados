package com.loschimbitas.parchados

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Activity5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_5)

        val boton: Button = findViewById(R.id.button)
        boton.setOnClickListener {
            val intent = Intent(this, Activity6::class.java)
            startActivity(intent)
        }
    }
}