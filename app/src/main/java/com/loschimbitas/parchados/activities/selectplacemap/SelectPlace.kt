package com.loschimbitas.parchados.activities.selectplacemap

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.loschimbitas.parchados.activities.parchar.CreateParche
import com.loschimbitas.parchados.databinding.ActivitySelectPlaceBinding

class SelectPlace : AppCompatActivity() {

    private lateinit var binding: ActivitySelectPlaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectPlaceBinding.inflate(layoutInflater)
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
        verifyRoot()
        initListeners()
    }

    /**
     * @Name: verifyRoot
     * @Description: Verify if the activity was called from the CreateParche activity.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     * @Annotations: SuppressLint("SetTextI18n") is used to avoid the warning of the setText method.
     */
    @SuppressLint("SetTextI18n")
    private fun verifyRoot() {
        val root = intent.getStringExtra("root")
        if (root == "CreateParche")
            binding.titleCreate.text = binding.titleCreate.text.toString() + " parchar!"
        else if (root == "CreateClass")
            binding.titleCreate.text = binding.titleCreate.text.toString() + " teach!"
        else
            onBackPressed()
    }

    /**
     * @Name: initListeners
     * @Description: Initialize the listeners for the buttons in the activity.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun initListeners() {
        setUpOnCallBack()
    }

    /**
     * @Name: setUpOnCallBack
     * @Description: Set up the listener for the back button.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun setUpOnCallBack() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val root = intent.getStringExtra("root")
                var intentChange: Intent? = null
                if (root == "CreateParche")
                    intentChange = Intent(this@SelectPlace, CreateParche::class.java)
//                else if (root == "CreateClass")
//                    startActivity(Intent(this@SelectPlace, CreateClass::class.java))
                else
                    onBackPressed()

                startActivity(intentChange)
                finish()
            }
        }

        // Agrega el callback a la lista de callbacks
        onBackPressedDispatcher.addCallback(this, callback)
    }
}