package com.loschimbitas.parchados

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val boton1: Button = findViewById(R.id.button1)
        boton1.setOnClickListener {
            val intent = Intent(this, Activity1::class.java)
            startActivity(intent)
        }

        val boton2: Button = findViewById(R.id.button2)
        boton2.setOnClickListener {
            val intent = Intent(this, Activity3::class.java)
            startActivity(intent)
        }
    }
}