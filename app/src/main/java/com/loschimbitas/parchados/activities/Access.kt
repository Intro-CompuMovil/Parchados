package com.loschimbitas.parchados.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.loschimbitas.parchados.R
import com.loschimbitas.parchados.activities.globales.Globales
import com.loschimbitas.parchados.activities.globales.Globales.Companion.userGlobal
import com.loschimbitas.parchados.activities.home.Parchar
import com.loschimbitas.parchados.activities.signup.SignUpInformationActivity
import com.loschimbitas.parchados.databinding.ActivityAccessBinding

class Access : AppCompatActivity() {

    private lateinit var binding: ActivityAccessBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        initialize()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            userGlobal.email = currentUser.email.toString()
            userGlobal.username = currentUser.displayName.toString()

            val intent = Intent(this, Parchar::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
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
        binding.inputPassword.text.clear()
        binding.inputUserOrEmail.text.clear()
    }

    /**
     * @Name: initListeners
     * @Description: Initialize the listeners for the buttons in the activity.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun initListeners() {
        togglePasswordVisibility()
        setUpSignUpListener()
        setUpSignInListener()
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

    /**
     * @Name: setUpSignInListener
     * @Description: Set up the listener for the sign in button.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun setUpSignInListener() {
        binding.buttonSignIn.setOnClickListener {
            if (binding.inputPassword.text.isEmpty() || binding.inputUserOrEmail.text.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Change to the parchar information screen
            val email = binding.inputUserOrEmail.text.toString()
            val password = binding.inputPassword.text.toString()

            if (!isEmailValid(email)) {
                // Mostrar un mensaje de error si el correo electrónico no es válido
                Toast.makeText(this, "invalid email", Toast.LENGTH_SHORT).show()

            }

            // Realizar la autenticación de usuario con Firebase
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {

                        showSignInNotification()
                        // La autenticación fue exitosa
                        auth.currentUser
                        val intent = Intent(this, Parchar::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish() // Esto cierra la actividad actual (inicio de sesión)
                    } else {
                        // La autenticación falló
                        Toast.makeText(this, "Failed authentication", Toast.LENGTH_SHORT).show()
                    }
                })

        }
    }

    private fun showSignInNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "mi_canal_id"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Nombre del Canal",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.notification)
            .setContentTitle("Inicio de Sesión Exitoso")
            .setContentText("¡Bienvenido de nuevo!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .extend(
                NotificationCompat.WearableExtender()
                    .setBridgeTag("tagOne"))
        notificationManager.notify(1, builder.build())


    }
    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


}