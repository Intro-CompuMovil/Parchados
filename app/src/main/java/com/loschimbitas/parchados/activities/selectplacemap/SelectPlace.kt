package com.loschimbitas.parchados.activities.selectplacemap

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.loschimbitas.parchados.R
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
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Overlay
import org.osmdroid.views.overlay.Polyline
import java.io.IOException
import java.util.Locale

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
    private var previousMarker: Marker? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        askForPermissions()

        roadManager = OSRMRoadManager(this, "ANDROID")

        initialize()

        // Agrega el Listener para detectar clics en el mapa
        val mapClickListener = MapClickListener()
        binding.osmMap.overlays.add(mapClickListener)
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

    private fun showMarker(geoPoint: GeoPoint, markerName: String, tipo: String): Marker {
        // Crea y muestra un nuevo marcador en la ubicación proporcionada
        val marker = Marker(binding.osmMap)
        marker.title = markerName
        marker.position = geoPoint

        marker.setOnMarkerClickListener { _, _ ->
            //calculateRouteToMarker(geoPoint)
            showCustomToast(markerName, getMarkerIconResource(tipo))
            true
        }

        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

        // Icono personalizado según el deporte
        when (tipo) {
            "UBI" -> marker.icon = resources.getDrawable(R.drawable.persona, theme)
            "PING" -> marker.icon = resources.getDrawable(R.drawable.pingpong, theme)
            "TENI" -> marker.icon = resources.getDrawable(R.drawable.tenis, theme)
            "BASK" -> marker.icon = resources.getDrawable(R.drawable.basketball, theme)
            "FUTB" -> marker.icon = resources.getDrawable(R.drawable.futbol, theme)
            "VOLE" -> marker.icon = resources.getDrawable(R.drawable.voleibol, theme)
        }

        binding.osmMap.overlays.add(marker)

        // Retorna el marcador creado
        return marker
    }


    private fun showCustomToast(markerName: String, markerIconResource: Int) {
        val inflater: LayoutInflater = layoutInflater
        val layout: View = inflater.inflate(R.layout.toast_custom, findViewById(R.id.toast_layout_root))

        val textView: TextView = layout.findViewById(R.id.textViewToast)

        if(markerName == "Mi ubicacion"){
            textView.text = "$markerName"
        }else{
            textView.text = "En camino a $markerName"
        }

        val imageView: ImageView = layout.findViewById(R.id.imageViewToast)
        imageView.setImageResource(markerIconResource)

        val toast = Toast(this)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }


    private fun getMarkerIconResource(tipo: String): Int {
        return when (tipo) {
            "UBI" -> R.drawable.persona
            "PING" -> R.drawable.pingpong
            "TENI" -> R.drawable.tenis
            "BASK" -> R.drawable.basketball
            "FUTB" -> R.drawable.futbol
            "VOLE" -> R.drawable.voleibol
            else -> R.drawable.parche
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


    private inner class MapClickListener : Overlay() {
        override fun onSingleTapConfirmed(event: MotionEvent, mapView: MapView): Boolean {
            val projection = mapView.projection
            val touchedPoint = projection.fromPixels(event.x.toInt(), event.y.toInt())

            val selectedLocation = Location("")
            selectedLocation.latitude = touchedPoint.latitude
            selectedLocation.longitude = touchedPoint.longitude

            // Muestra la dirección en un Toast
            showCustomToast("Dirección: ${getAddressFromLocation(selectedLocation)}", R.drawable.tenis)

            // Elimina el marcador anterior si existe
            previousMarker?.let {
                binding.osmMap.overlays.remove(it)
            }

            // Agrega un nuevo marcador
            val newMarker = showMarker(GeoPoint(touchedPoint.latitude, touchedPoint.longitude), "Ubicación seleccionada", "SELECCIONADA")

            // Actualiza la referencia al nuevo marcador como el anterior
            previousMarker = newMarker

            return true
        }
    }


    private fun getAddressFromLocation(location: Location): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>?

        try {
            addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        } catch (e: IOException) {
            e.printStackTrace()
            return "Dirección no disponible"
        }

        return if (addresses != null && addresses.isNotEmpty()) {
            addresses[0].getAddressLine(0)
        } else {
            "Dirección no disponible"
        }
    }

}