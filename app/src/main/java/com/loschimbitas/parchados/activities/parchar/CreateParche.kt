package com.loschimbitas.parchados.activities.parchar

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.loschimbitas.parchados.activities.home.Parchar
import com.loschimbitas.parchados.activities.selectplacemap.SelectPlace
import com.loschimbitas.parchados.databinding.ActivityCreateParcheBinding
import com.loschimbitas.parchados.model.Parche
import com.loschimbitas.parchados.activities.globales.Globales.Companion.userGlobal

class CreateParche : AppCompatActivity() {

    private val SELECT_PLACE_REQUEST_CODE = 1

    private lateinit var binding: ActivityCreateParcheBinding

//    Base de datos
    private val database = FirebaseDatabase.getInstance()
    private lateinit var myRef: DatabaseReference
//    fin variables base de datos

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
        buttonGoListenes()
    }

    private fun buttonGoListenes() {
        binding.buttonGo.setOnClickListener {
            if (!validateForm()) {
                return@setOnClickListener
            }
            guardarDatosFirebase()
            startActivity(Intent(this, Parchar::class.java))
        }

    }

    private fun guardarDatosFirebase() {
        val sport = binding.edittextSearchSport.text.toString()
        val place = binding.edittextPlace.text.toString()
        val level = binding.edittextLevel.text.toString()
        val time = binding.edittextTime.text.toString()
        val parche = Parche(sport, place, level, time)
        myRef = database.getReference("parches/${userGlobal.email}/")
        myRef.setValue(parche)

    }

    private fun validateForm(): Boolean {
        var valid = true
        val sport = binding.edittextSearchSport.text.toString()
        if (TextUtils.isEmpty(sport)) {
            binding.edittextSearchSport.error = "Required."
            valid = false
        } else {
            binding.edittextSearchSport.error = null
        }
        val place = binding.edittextPlace.text.toString()
        if (TextUtils.isEmpty(place)) {
            binding.edittextPlace.error = "Required."
            valid = false
        } else {
            binding.edittextPlace.error = null
        }
        val level = binding.edittextLevel.text.toString()
        if (TextUtils.isEmpty(level)) {
            binding.edittextLevel.error = "Required."
            valid = false
        } else {
            binding.edittextLevel.error = null
        }
        val time = binding.edittextTime.text.toString()
        if (TextUtils.isEmpty(time)) {
            binding.edittextTime.error = "Required."
            valid = false
        } else {
            binding.edittextTime.error = null
        }
        return valid
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