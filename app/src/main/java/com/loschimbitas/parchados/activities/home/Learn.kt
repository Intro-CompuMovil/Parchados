package com.loschimbitas.parchados.activities.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loschimbitas.parchados.databinding.ActivityLearnBinding

class Learn : AppCompatActivity() {

    private lateinit var binding: ActivityLearnBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonParchar.setOnClickListener {
            startActivity(Intent(this, Parchar::class.java))
            finish()
        }

//        For users that have not permission to start class (students)
//        binding.linearLayoutStartClass.layoutParams.height = (5 * getResources().getDisplayMetrics().density + 0.5f).toInt()
//        binding.textViewStartClass.text = ""
//        binding.textViewStartClass.textSize = 0f
//        binding.buttonStartClass.visibility = LinearLayout.INVISIBLE
//        binding.buttonStartClass.isEnabled = false

    }
}