package com.loschimbitas.parchados.activities.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import com.loschimbitas.parchados.activities.configuration.ConfigurationMenu
import com.loschimbitas.parchados.activities.globales.Globales
import com.loschimbitas.parchados.activities.learning.CreateClass
import com.loschimbitas.parchados.activities.learning.JoinAClass
import com.loschimbitas.parchados.databinding.ActivityLearnBinding
import com.loschimbitas.parchados.databinding.ActivityParcharBinding
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.library.BuildConfig
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Polyline

class Learn : AppCompatActivity() {

    private lateinit var binding: ActivityLearnBinding
    private var roadOverlay: Polyline?= null
    private lateinit var roadManager: RoadManager


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
        binding = ActivityLearnBinding.inflate(layoutInflater)
        setContentView(binding.root)

        askForPermissions()

//        For users that have not permission to start class (students)
//        binding.linearLayoutStartClass.layoutParams.height = (5 * getResources().getDisplayMetrics().density + 0.5f).toInt()
//        binding.textViewStartClass.text = ""
//        binding.textViewStartClass.textSize = 0f
//        binding.buttonStartClass.visibility = LinearLayout.INVISIBLE
//        binding.buttonStartClass.isEnabled = false


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

    override fun onPause() {
        super.onPause()
        binding.osmMap.onPause()
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
        setUpPlayerInformation()
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
        setUpCreateClassListener()
        setUpParcharListener()
        setUpMapViewListener()
        setUpConfigurationListener()
    }

    /**
     * @Name: setUpCreateClassListener
     * @Description: Set up the listener for the button that takes the user to the CreateClass activity.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun setUpCreateClassListener() {
        binding.buttonCreateClass.setOnClickListener {
            startActivity(Intent(this, CreateClass::class.java))
        }
    }

    /**
     * @Name: setUpParcharListener
     * @Description: Set up the listener for the button that takes the user to the Parchar activity.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun setUpParcharListener() {
        binding.buttonParchar.setOnClickListener {
            startActivity(Intent(this, Parchar::class.java))
            finish()
        }
    }

    /**
     * @Name: setUpParcharListener
     * @Description: Set up the listener for the button that takes the user to the Parchar activity.
     * @Parameters: None.
     * @Return: None.
     * @Throws: None.
     */
    private fun setUpMapViewListener() {
//        binding.mapView.setOnClickListener {
//            startActivity(Intent(this, JoinAClass::class.java))
//        }
        Configuration.getInstance().userAgentValue = BuildConfig.BUILD_TYPE
        binding.osmMap.setTileSource(TileSourceFactory.MAPNIK)
        binding.osmMap.setMultiTouchControls(true)

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