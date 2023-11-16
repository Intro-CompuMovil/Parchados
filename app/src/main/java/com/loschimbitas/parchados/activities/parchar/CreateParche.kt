package com.loschimbitas.parchados.activities.parchar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loschimbitas.parchados.activities.selectplacemap.SelectPlace
import com.loschimbitas.parchados.databinding.ActivityCreateParcheBinding

class CreateParche : AppCompatActivity() {

    private val SELECT_PLACE_REQUEST_CODE = 1

    private lateinit var binding: ActivityCreateParcheBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateParcheBinding.inflate(layoutInflater)
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
            startActivityForResult(intent, SELECT_PLACE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_PLACE_REQUEST_CODE && resultCode == RESULT_OK) {
            // Recibe la dirección seleccionada desde SelectPlace
            val selectedAddress = data?.getStringExtra("selected_address")

            // Muestra la dirección en el EditText
            binding.edittextPlace.setText(selectedAddress)
        }
    }


}