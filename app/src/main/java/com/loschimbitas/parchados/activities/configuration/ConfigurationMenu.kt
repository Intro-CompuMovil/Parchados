package com.loschimbitas.parchados.activities.configuration

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loschimbitas.parchados.databinding.ActivityConfigurationMenuBinding

class ConfigurationMenu : AppCompatActivity() {

    private lateinit var binding: ActivityConfigurationMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigurationMenuBinding.inflate(layoutInflater)
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
        setUpProfileConfigurationListener()
    }

    /**
     * @Name: setUpProfileConfigurationListener
     * @Description: Set up the listener for the button that opens the profile configuration activity.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun setUpProfileConfigurationListener() {
        binding.buttonProfileConfiguration.setOnClickListener {
            startActivity(Intent(this, ProfileConfiguration::class.java))
        }
    }
}