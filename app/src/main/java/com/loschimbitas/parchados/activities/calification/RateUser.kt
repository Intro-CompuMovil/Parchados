package com.loschimbitas.parchados.activities.calification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loschimbitas.parchados.databinding.ActivityRateUserBinding

class RateUser : AppCompatActivity() {

    private lateinit var binding: ActivityRateUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRateUserBinding.inflate(layoutInflater)
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

    }
}