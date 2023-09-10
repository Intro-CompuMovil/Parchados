package com.loschimbitas.parchados.activities.parchar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loschimbitas.parchados.activities.selectplacemap.SelectPlace
import com.loschimbitas.parchados.databinding.ActivityCreateParcheBinding

class CreateParche : AppCompatActivity() {

    private lateinit var binding: ActivityCreateParcheBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateParcheBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        setUpSelectPlaceOnMap()
    }

    /**
     * @Name: setUpSelectPlaceOnMap
     * @Description: Set up the listener for the select place on map button.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun setUpSelectPlaceOnMap() {
        binding.buttonSelectPlaceOnMap.setOnClickListener {
            val intent = Intent(this, SelectPlace::class.java)
            intent.putExtra("root", "CreateParche")
            startActivity(intent)
            finish()
        }
    }


}