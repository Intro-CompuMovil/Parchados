package com.loschimbitas.parchados.activities.calification

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loschimbitas.parchados.activities.home.Parchar
import com.loschimbitas.parchados.databinding.ActivityShowUsersBinding

class ShowUsers : AppCompatActivity() {

    private lateinit var binding: ActivityShowUsersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowUsersBinding.inflate(layoutInflater)
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
        binding.textView.setOnClickListener {
            startActivity(Intent(this, RateUser::class.java))
        }
        setUpButtonReadyListener()
    }

    /**
     * @Name: setUpButtonReadyListener
     * @Description: Set up the listener for the button ready.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun setUpButtonReadyListener() {
        binding.buttonReady.setOnClickListener {
            val intent = Intent(this, Parchar::class.java)
            // This flag is used to clear the stack of activities and start a new one.
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}