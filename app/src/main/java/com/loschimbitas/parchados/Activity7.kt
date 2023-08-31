package com.loschimbitas.parchados

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Activity7 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_7)

        val boton1: Button = findViewById(R.id.button1)
        boton1.setOnClickListener {
            val intent = Intent(this, Activity8::class.java)
            startActivity(intent)
        }

        val boton2: Button = findViewById(R.id.button2)
        boton2.setOnClickListener {
            val intent = Intent(this, Activity3::class.java)
            startActivity(intent)
        }
    }
}