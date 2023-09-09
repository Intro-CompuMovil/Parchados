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

    private fun setUpSignUpListener() {
        binding.buttonSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpInformationActivity::class.java))
        }
    }

    private fun togglePasswordVisibility() {
        binding.buttonEyePassword.setOnClickListener {
            if (binding.inputPassword.transformationMethod == null)
                binding.inputPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            else
                binding.inputPassword.transformationMethod = null
        }
    }
}