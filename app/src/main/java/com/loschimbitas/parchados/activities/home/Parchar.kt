package com.loschimbitas.parchados.activities.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.loschimbitas.parchados.R
import com.loschimbitas.parchados.activities.configuration.ConfigurationMenu
import com.loschimbitas.parchados.activities.globales.Globales
import com.loschimbitas.parchados.activities.globales.Globales.Companion.userGlobal
import com.loschimbitas.parchados.activities.parchar.CreateParche
import com.loschimbitas.parchados.activities.parchar.JoinAParche
import com.loschimbitas.parchados.databinding.ActivityParcharBinding
import org.osmdroid.api.IMapController
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.Road
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.TilesOverlay
import java.io.IOException

class Parchar : AppCompatActivity() {

    private lateinit var binding: ActivityParcharBinding
    private var roadOverlay:Polyline?= null
    private lateinit var roadManager: RoadManager
    private lateinit var sportsListView: ListView

    private var selectedSportType: String? = null

    private val sportsList = listOf(
        Sport("Ping Pong", R.drawable.pingpong, "PING"),
        Sport("Tenis", R.drawable.tenis, "TENI"),
        Sport("Baloncesto", R.drawable.basketball,  "BASK"),
        Sport("Fútbol", R.drawable.futbol,  "FUTB"),
        Sport("Voleibol", R.drawable.voleibol,  "VOLE"),
        Sport("Todos", R.drawable.deportes,  "TODOS"),
    )


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

        roadManager = OSRMRoadManager(this, "ANDROID")

        sportsListView = findViewById(R.id.listview_sports)
        val sportsAdapter = SportsAdapter(this, sportsList)
        sportsListView.adapter = sportsAdapter


        sportsListView.setOnItemClickListener { _, _, position, _ ->
            val selectedSport = sportsList[position]
            selectedSportType = if (selectedSport.tipo == "TODOS") null else selectedSport.tipo
            showCustomToast(selectedSport.name, selectedSport.iconResourceId)
            refreshMarkers() // Actualiza los marcadores en el mapa
        }


        initialize()

    }

    // inicio onResume
    override fun onResume() {
        super.onResume()
        askForPermissions()


        // Variables de usuario global
        if (!userGlobal.imageUrl.equals("")) {
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
        setUpPlayerInformation()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpPlayerInformation() {
        if (userGlobal.imageUrl.isEmpty())
            binding.profileImage.setImageResource(R.drawable.icon_user)
        else
            binding.profileImage.setImageURI(userGlobal.imageUrl.toUri())

        binding.profileName.text = " " + userGlobal.username
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
    // ...

    // ...

    private fun setUpMapViewListener() {
        binding.osmMap.setOnClickListener {
            // ubicacion actual del usuario
            val currentLocation = getCurrentLocation()

            // Verifica si se pudo obtener la ubicación actual
            if (currentLocation != null) {
                // Centra el mapa en la ubicación actual del usuario
                val mapController: IMapController = binding.osmMap.controller
                mapController.setZoom(18.0)
                mapController.setCenter(GeoPoint(currentLocation.latitude, currentLocation.longitude))

                // Muestra un marcador en la ubicación actual
                showMarker(GeoPoint(currentLocation.latitude, currentLocation.longitude),"Mi ubicacion" , "UBI")
            } else {
                // cuando no se puede obtener la ubicacion actual
            }

        }
    }


    // Método para mostrar el marcador en la ubicación actual
    private fun showMarker(geoPoint: GeoPoint, markerName: String, tipo: String) {
        // Verifica si se ha seleccionado un tipo y si el tipo coincide con el marcador actual o es tipo "UBI"
        if (selectedSportType == null || selectedSportType == tipo || tipo == "UBI") {
            // Crea y muestra un nuevo marcador en la ubicación proporcionada
            val marker = Marker(binding.osmMap)
            marker.title = markerName
            marker.position = geoPoint

            marker.setOnMarkerClickListener { _, _ ->
                calculateRouteToMarker(geoPoint)
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
        }
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

    private fun refreshMarkers() {
        binding.osmMap.overlays.clear() // Limpia todos los overlays actuales

        // Obtén la ubicación actual
        val currentLocation = getCurrentLocation()

        // Verificación de ubicación actual
        if (currentLocation != null) {
            val mapController: IMapController = binding.osmMap.controller

            mapController.setZoom(18.0)
            mapController.setCenter(GeoPoint(currentLocation.latitude, currentLocation.longitude))

            // Establecimiento de puntos(marcadores)
            showMarker(GeoPoint(currentLocation.latitude, currentLocation.longitude), "Mi ubicacion", "UBI")
            showMarker(GeoPoint(4.6318, -74.0667), "Ping Pong", "PING")
            showMarker(GeoPoint(4.6285, -74.0647), "Fulbol", "FUTB")
            showMarker(GeoPoint(4.6256, -74.0653), "Tenis", "TENI")
            showMarker(GeoPoint(4.6454, -74.0618), "Voleibol", "VOLE")
            showMarker(GeoPoint(4.6318, -74.0615), "Basketball", "BASK")
        } else {
            Toast.makeText(this, "Ubicación no encontrada", Toast.LENGTH_SHORT).show()
        }
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
    
    private fun calculateRouteToMarker(destinationPoint: GeoPoint) {
        // Inicia la tarea asíncrona para calcular la ruta
        GetRouteTask().execute(destinationPoint)
    }

    private inner class GetRouteTask : AsyncTask<GeoPoint, Void, Road>() {
        override fun doInBackground(vararg params: GeoPoint): Road? {
            val routePoints = ArrayList<GeoPoint>()

            // Obtener la ubicación actual del usuario
            val currentLocation = getCurrentLocation()
            if (currentLocation != null) {
                routePoints.add(GeoPoint(currentLocation.latitude, currentLocation.longitude))
            } else {
                //showToast("No se pudo obtener la ubicación actual")
                return null
            }

            // Agregar el destino a los puntos de la ruta
            routePoints.add(params[0]) // Destino

            return roadManager.getRoad(routePoints)
        }

        override fun onPostExecute(result: Road?) {
            super.onPostExecute(result)
            if (result != null) {
                // Dibujar la ruta
                drawRoadOverlay(result)
            } else {
                //showToast("Error al obtener la ruta")
            }
        }
    }


    private fun drawRoadOverlay(road: Road) {
        roadOverlay?.let { binding.osmMap.overlays.remove(it) }
        roadOverlay = RoadManager.buildRoadOverlay(road)
        roadOverlay?.outlinePaint?.color = ContextCompat.getColor(this, R.color.red)
        roadOverlay?.outlinePaint?.strokeWidth = 10f
        binding.osmMap.overlays.add(roadOverlay)
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