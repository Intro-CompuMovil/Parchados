package com.loschimbitas.parchados.activities.parchar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loschimbitas.parchados.activities.active.InProgress
import com.loschimbitas.parchados.databinding.ActivityJoinAParcheBinding

class JoinAParche : AppCompatActivity() {

    private lateinit var binding: ActivityJoinAParcheBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinAParcheBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialize()
    }

    /**
     * @Name: initialize
     * @Description: Initialize the activity.
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
            intent.putExtra("type", "parche")
            startActivity(intent)
            finish()
        }
    }
}