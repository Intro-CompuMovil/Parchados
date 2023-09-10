package com.loschimbitas.parchados.activities.active

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loschimbitas.parchados.databinding.ActivityInProgressBinding

class InProgress : AppCompatActivity() {

    private lateinit var binding: ActivityInProgressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInProgressBinding.inflate(layoutInflater)
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
        verifyType()
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

    private fun verifyType() {
        val type = intent.getStringExtra("type")
        if (type == "parche")
            binding.aboutTitle.text = "Parche"
        else if (type == "class")
            binding.aboutTitle.text = "Class"
    }
}