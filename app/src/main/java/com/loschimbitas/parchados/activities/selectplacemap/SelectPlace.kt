package com.loschimbitas.parchados.activities.selectplacemap

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.loschimbitas.parchados.activities.globales.Globales
import com.loschimbitas.parchados.activities.home.Parchar
import com.loschimbitas.parchados.activities.learning.CreateClass
import com.loschimbitas.parchados.activities.parchar.CreateParche
import com.loschimbitas.parchados.databinding.ActivityLearnBinding
import com.loschimbitas.parchados.databinding.ActivitySelectPlaceBinding
import org.osmdroid.api.IMapController
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Polyline

class SelectPlace : AppCompatActivity() {

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

    private lateinit var binding: ActivitySelectPlaceBinding
    private var roadOverlay: Polyline?= null
    private lateinit var roadManager: RoadManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        askForPermissions()

        roadManager = OSRMRoadManager(this, "ANDROID")

        initialize()
    }

    // inicio solicitud permisos
    private fun askForPermissions() {
        locationPermissions.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
    }

    override fun onResume() {
        super.onResume()
        if (!Globales.userGlobal.imageUrl.equals("")) {
            setUpPlayerInformation()
        }

        // Variables para mapas
        binding.osmMap.onResume()


        // ubicacon actual
        val currentLocation = getCurrentLocation()

        // Verificacion de ubicacion actual
        if (currentLocation != null) {
            val mapController: IMapController = binding.osmMap.controller

            mapController.setZoom(18.0)
            mapController.setCenter(GeoPoint(currentLocation.latitude, currentLocation.longitude))

            // establecimiento de puntos(marcadores)
            showMarker(GeoPoint(currentLocation.latitude, currentLocation.longitude),"Mi ubicacion","UBI")
            showMarker(GeoPoint(4.6318,-74.0667),"Ping Pong", "PING")
            showMarker(GeoPoint(4.6285,-74.0647),"Fulbol","FUTB")
            showMarker(GeoPoint(4.6256,-74.0653),"Tenis","TENI")
            showMarker(GeoPoint(4.6454,-74.0618),"Voleibol","VOLE")
            showMarker(GeoPoint(4.6318,-74.0615),"Basketball","BASK")

        } else {
            Toast.makeText(this, "Ubicación no encontrada", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getCurrentLocation(): Location? {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return try {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return null
            }
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        } catch (e: Exception) {
            e.printStackTrace()
            null
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
        verifyRoot()
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

    override fun onPause() {
        super.onPause()
        binding.osmMap.onPause()
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
                else if (root == "CreateClass")
                    intentChange = Intent(this@SelectPlace, CreateClass::class.java)
                else
                    intentChange = Intent(this@SelectPlace, Parchar::class.java)

                startActivity(intentChange)
                finish()
            }
        }

        // Agrega el callback a la lista de callbacks
        onBackPressedDispatcher.addCallback(this, callback)
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
}