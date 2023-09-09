package com.loschimbitas.parchados

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.loschimbitas.parchados.activities.Access

class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Show the app logo for 2 seconds and change to the Access screen
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, Access::class.java)) // Change to Access screen
            finish() // Optional, to close the current activity
        }, 2000) // 2000 milliseconds (2 seconds)
    }
}