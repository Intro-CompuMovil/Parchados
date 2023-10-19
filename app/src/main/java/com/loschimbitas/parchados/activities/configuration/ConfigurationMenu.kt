package com.loschimbitas.parchados.activities.configuration

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.loschimbitas.parchados.R
import com.loschimbitas.parchados.activities.Access
import com.loschimbitas.parchados.activities.globales.Globales.Companion.userGlobal
import com.loschimbitas.parchados.databinding.ActivityConfigurationMenuBinding

class ConfigurationMenu : AppCompatActivity() {

    private lateinit var binding: ActivityConfigurationMenuBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigurationMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

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
        setUpPlayerInformation()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpPlayerInformation() {
        if (userGlobal.imageUrl.isEmpty())
            binding.profileImage.setImageResource(R.drawable.icon_user)
        else
            binding.profileImage.setImageURI(userGlobal.imageUrl.toUri())

        binding.profileName.text = " "+ userGlobal.username
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
        SetUpSignOutListener()
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

    private fun SetUpSignOutListener(){
        binding.buttonSignOut.setOnClickListener {
            auth.signOut()
            // Redirigir al usuario de nuevo a la pantalla de inicio
            val intent = Intent(this, Access::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish() // Esto cierra la actividad actual
        }
    }
}