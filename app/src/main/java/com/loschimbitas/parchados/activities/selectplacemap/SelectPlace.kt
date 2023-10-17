package com.loschimbitas.parchados.activities.selectplacemap

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.loschimbitas.parchados.activities.home.Parchar
import com.loschimbitas.parchados.activities.learning.CreateClass
import com.loschimbitas.parchados.activities.parchar.CreateParche
import com.loschimbitas.parchados.databinding.ActivityLearnBinding
import com.loschimbitas.parchados.databinding.ActivitySelectPlaceBinding
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.views.overlay.Polyline

class SelectPlace : AppCompatActivity() {

    private lateinit var binding: ActivitySelectPlaceBinding
    private var roadOverlay: Polyline?= null
    private lateinit var roadManager: RoadManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectPlaceBinding.inflate(layoutInflater)
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