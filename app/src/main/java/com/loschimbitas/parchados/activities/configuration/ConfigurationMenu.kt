package com.loschimbitas.parchados.activities.configuration

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.loschimbitas.parchados.activities.globales.Globales.Companion.userGlobal
import com.loschimbitas.parchados.databinding.ActivityConfigurationMenuBinding

class ConfigurationMenu : AppCompatActivity() {

    private lateinit var binding: ActivityConfigurationMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigurationMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialize()
    }

    override fun onResume() {
        super.onResume()
        if (!userGlobal.imageUrl.equals("")) {
            setUpPlayerInformation()
        }
    }

//    override fun onRestart() {
//        super.onRestart()
//        if (!userGlobal.imageUrl.equals("") && !userGlobal.username.equals("")) {
//            setUpPlayerInformation()
//        }
//    }

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

    private fun setUpPlayerInformation() {
        binding.profileImage.setImageURI(userGlobal.imageUrl.toUri())
        binding.profileName.text = userGlobal.username
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
        setUpBeProfessorListener()
        setUpDeleteAccountListener()
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

    /**
     * @Name: setUpBeProfessorListener
     * @Description: Set up the listener for the button that opens the be professor activity.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun setUpBeProfessorListener() {
        binding.buttonBeProfessor.setOnClickListener {
            startActivity(Intent(this, BeProfessor::class.java))
        }
    }

    /**
     * @Name: setUpDeleteAccountListener
     * @Description: Set up the listener for the button that opens the delete account activity.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun setUpDeleteAccountListener() {
        binding.buttonDeleteAccount.setOnClickListener {
            startActivity(Intent(this, DeleteAccount::class.java))
        }
    }
}