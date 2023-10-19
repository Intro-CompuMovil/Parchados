package com.loschimbitas.parchados.activities.signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.loschimbitas.parchados.activities.globales.Globales.Companion.userGlobal
import com.loschimbitas.parchados.activities.home.Parchar
import com.loschimbitas.parchados.databinding.ActivitySignUpInformationBinding

class SignUpInformationActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpInformationBinding.inflate(layoutInflater)
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
        saveChanges()
        clearInputs()
    }

    private fun clearInputs(){
        binding.inputEmail.text.clear()
        binding.inputEmailConfirmation.text.clear()
        binding.inputFirstName.text.clear()
        binding.inputLastName.text.clear()
        binding.inputPassword.text.clear()
        binding.inputPasswordConfirmation.text.clear()
        binding.inputUsername.text.clear()
    }

    private fun saveChanges() {

        binding.buttonCreateAccount.setOnClickListener {
            if (binding.inputFirstName.text.toString().isEmpty()) {
                binding.inputFirstName.error = "Please enter your first name"
                return@setOnClickListener
            }
            if (binding.inputLastName.text.toString().isEmpty()) {
                binding.inputLastName.error = "Please enter your last name"
                return@setOnClickListener
            }
            if (binding.inputUsername.text.toString().isEmpty()) {
                binding.inputUsername.error = "Please enter your username"
                return@setOnClickListener
            }
            if (binding.inputEmail.text.toString().isEmpty()) {
                binding.inputEmail.error = "Please enter your email"
                return@setOnClickListener
            }
            if (!binding.inputEmail.text.toString()
                    .equals(binding.inputEmailConfirmation.text.toString())
            ) {
                binding.inputEmailConfirmation.error = "Emails do not match"
                return@setOnClickListener
            }
            if (!binding.inputEmail.text.toString().contains("@")) {
                binding.inputEmail.error = "Please enter a valid email"
                return@setOnClickListener
            }
            if (binding.inputEmailConfirmation.text.toString().isEmpty()) {
                binding.inputEmailConfirmation.error = "Please confirm your email"
                return@setOnClickListener
            }

            if (binding.inputPassword.text.toString().isEmpty()) {
                binding.inputPassword.error = "Please enter your password"
                return@setOnClickListener
            }
            if (binding.inputPassword.text.toString().length < 6) {
                binding.inputPassword.error = "Password must be at least 6 characters long"
                return@setOnClickListener
            }
            if (binding.inputPasswordConfirmation.text.toString().isEmpty()) {
                binding.inputPasswordConfirmation.error = "Please confirm your password"
                return@setOnClickListener
            }
            if (!binding.inputPassword.text.toString()
                    .equals(binding.inputPasswordConfirmation.text.toString())
            ) {
                binding.inputPasswordConfirmation.error = "Passwords do not match"
                return@setOnClickListener
            }

            userGlobal.firstName = binding.inputFirstName.text.toString()
            userGlobal.lastName = binding.inputLastName.text.toString()
            userGlobal.username = binding.inputUsername.text.toString()
            userGlobal.email = binding.inputEmail.text.toString()
            userGlobal.password = binding.inputPassword.text.toString()

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(userGlobal.email, userGlobal.password)
            // save display name (username)
            FirebaseAuth.getInstance().currentUser?.updateProfile(
                com.google.firebase.auth.UserProfileChangeRequest.Builder()
                    .setDisplayName(userGlobal.username)
                    .build()
            )

            val intent = Intent(this, Parchar::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}