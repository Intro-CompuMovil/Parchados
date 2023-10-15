package com.loschimbitas.parchados.activities.home

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.loschimbitas.parchados.activities.configuration.ConfigurationMenu
import com.loschimbitas.parchados.activities.globales.Globales
import com.loschimbitas.parchados.activities.parchar.CreateParche
import com.loschimbitas.parchados.activities.parchar.JoinAParche
import com.loschimbitas.parchados.databinding.ActivityParcharBinding
import org.osmdroid.api.IMapController
import org.osmdroid.util.GeoPoint

class Parchar : AppCompatActivity() {

    private lateinit var binding: ActivityParcharBinding

    // Variables del mapa
    val latitude = 4.62
    val longitude = -74.07
    val startPoint = GeoPoint(latitude, longitude)


    private val locationPermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParcharBinding.inflate(layoutInflater)
        setContentView(binding.root)

        askForPermissions()

        initialize()



    }

    // inicio onResume
    override fun onResume() {
        super.onResume()
        // Variables de usuario global
        if (!Globales.userGlobal.imageUrl.equals("")) {
            setUpPlayerInformation()
        }

        // Variables para mapas
        binding.osmMap.onResume()
        val mapController: IMapController = binding.osmMap.controller
        mapController.setZoom(18.0)
        mapController.setCenter(this.startPoint)
    }
    // fin onResume

    // inicio onPause
    override fun onPause() {
        super.onPause()
        binding.osmMap.onPause()
    }
    // fin onPause


    // inicio solicitud permisos
    private fun askForPermissions() {
        locationPermissions.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
    }
    // fin solicitud permisos


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

    private fun setUpPlayerInformation() {
        binding.profileImage.setImageURI(Globales.userGlobal.imageUrl.toUri())
        binding.profileName.text = Globales.userGlobal.username
    }

    /**
     * @Name: initListeners
     * @Description: Initialize the listeners for the buttons in the activity.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun initListeners() {
        setUpCreateParcheListener()
        setUpLearnListener()
        setUpMapViewListener()
        setUpConfigurationListener()
    }



    /**
     * @Name: setUpCreateParcheListener
     * @Description: Set up the listener for the button that takes the user to the CreateParche activity.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun setUpCreateParcheListener() {
        binding.buttonCreateParche.setOnClickListener {
            startActivity(Intent(this, CreateParche::class.java))
        }
    }

    /**
     * @Name: setUpLearnListener
     * @Description: Set up the listener for the button that takes the user to the Learn activity.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun setUpLearnListener() {
        binding.buttonLearn.setOnClickListener {
            startActivity(Intent(this, Learn::class.java))
            finish()
        }
    }

    /**
     * @Name: setUpMapViewListener
     * @Description: Set up the listener for the map view.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun setUpMapViewListener() {
//        binding.mapView.setOnClickListener {
//            startActivity(Intent(this, JoinAParche::class.java))
//        }
//        El mapa debe inicializarse en la ubicación actual del usuario
        val mapController: IMapController = binding.osmMap.controller
        mapController.setZoom(18.0)
        mapController.setCenter(this.startPoint)
    }

    /**
     * @Name: setUpConfigurationListener
     * @Description: Set up the listener for the configuration button.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun setUpConfigurationListener() {
        binding.profileImage.setOnClickListener {
            startActivity(Intent(this, ConfigurationMenu::class.java))
        }
    }
}