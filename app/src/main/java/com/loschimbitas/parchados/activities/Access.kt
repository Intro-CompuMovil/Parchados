package com.loschimbitas.parchados.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import com.loschimbitas.parchados.activities.signup.SignUpInformationActivity
import com.loschimbitas.parchados.databinding.ActivityAccessBinding

class Access : AppCompatActivity() {

    private lateinit var binding: ActivityAccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        togglePasswordVisibility()
        setUpSignUpListener()

    }

    /**
     * @Name: togglePasswordVisibility
     * @Description: Set up the listener for the eye button to toggle the password visibility.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun togglePasswordVisibility() {
        binding.buttonEyePassword.setOnClickListener {
            if (binding.inputPassword.transformationMethod == null) // If the password is visible
                // Hide the password
                binding.inputPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            else // If the password is hidden
                binding.inputPassword.transformationMethod = null // Show the password
        }
    }

    /**
     * @Name: setUpSignUpListener
     * @Description: Set up the listener for the sign up button.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun setUpSignUpListener() {
        binding.buttonSignUp.setOnClickListener {
            // Change to the sign up information screen
            startActivity(Intent(this, SignUpInformationActivity::class.java))
        }
    }
}