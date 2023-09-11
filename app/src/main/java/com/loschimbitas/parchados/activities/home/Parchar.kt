package com.loschimbitas.parchados.activities.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loschimbitas.parchados.activities.configuration.ConfigurationMenu
import com.loschimbitas.parchados.activities.parchar.CreateParche
import com.loschimbitas.parchados.activities.parchar.JoinAParche
import com.loschimbitas.parchados.databinding.ActivityParcharBinding

class Parchar : AppCompatActivity() {

    private lateinit var binding: ActivityParcharBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParcharBinding.inflate(layoutInflater)
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
        setUpCreateParcheListener()
        setUpLearnListener()
        setUpMapViewListener()
        setUpConfigurationListener()
    }

    /**
     * @Name: setUpCreateParcheListener
     * @Description: Set up the listener for the button that takes the user to the CreateParche activity.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun setUpCreateParcheListener() {
        binding.buttonCreateParche.setOnClickListener {
            startActivity(Intent(this, CreateParche::class.java))
        }
    }

    /**
     * @Name: setUpLearnListener
     * @Description: Set up the listener for the button that takes the user to the Learn activity.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun setUpLearnListener() {
        binding.buttonLearn.setOnClickListener {
            startActivity(Intent(this, Learn::class.java))
            finish()
        }
    }

    /**
     * @Name: setUpMapViewListener
     * @Description: Set up the listener for the map view.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun setUpMapViewListener() {
        binding.mapView.setOnClickListener {
            startActivity(Intent(this, JoinAParche::class.java))
        }
    }

    /**
     * @Name: setUpConfigurationListener
     * @Description: Set up the listener for the configuration button.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun setUpConfigurationListener() {
        binding.profileImage.setOnClickListener {
            startActivity(Intent(this, ConfigurationMenu::class.java))
        }
    }
}