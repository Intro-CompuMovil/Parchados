package com.loschimbitas.parchados.activities.learning

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loschimbitas.parchados.activities.active.InProgress
import com.loschimbitas.parchados.databinding.ActivityJoinAClassBinding

class JoinAClass : AppCompatActivity() {

    private lateinit var binding: ActivityJoinAClassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinAClassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialize()
    }

    /**
     * @Name: initListeners
     * @Description: Initialize the listeners for the buttons in the activity.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun initialize() {
        initListeners()
    }

    /**
     * @Name: initListeners
     * @Description: Initialize the listeners for the buttons in the activity.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun initListeners() {
        setUpButtonListener()
    }

    /**
     * @Name: setUpButtonListener
     * @Description: Set up the listener for the button that takes the user to the InProgress activity.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun setUpButtonListener() {
        binding.button.setOnClickListener {
            val intent = Intent(this, InProgress::class.java)
            intent.putExtra("type", "class")
            startActivity(intent)
            finish()
        }
    }
}