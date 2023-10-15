package com.loschimbitas.parchados.activities.home

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.loschimbitas.parchados.activities.configuration.ConfigurationMenu
import com.loschimbitas.parchados.activities.globales.Globales
import com.loschimbitas.parchados.activities.learning.CreateClass
import com.loschimbitas.parchados.activities.learning.JoinAClass
import com.loschimbitas.parchados.databinding.ActivityLearnBinding
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.library.BuildConfig
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.MapEventsOverlay

class Learn : AppCompatActivity() {

    private lateinit var binding: ActivityLearnBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        For users that have not permission to start class (students)
//        binding.linearLayoutStartClass.layoutParams.height = (5 * getResources().getDisplayMetrics().density + 0.5f).toInt()
//        binding.textViewStartClass.text = ""
//        binding.textViewStartClass.textSize = 0f
//        binding.buttonStartClass.visibility = LinearLayout.INVISIBLE
//        binding.buttonStartClass.isEnabled = false
        initialize()
    }

    override fun onResume() {
        super.onResume()
        if (!Globales.userGlobal.imageUrl.equals("")) {
            setUpPlayerInformation()
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