package com.loschimbitas.parchados.activities.configuration

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.loschimbitas.parchados.activities.globales.Globales.Companion.userGlobal
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.loschimbitas.parchados.R
import com.loschimbitas.parchados.activities.globales.Globales
import com.loschimbitas.parchados.databinding.ActivityProfileConfigurationBinding
import java.io.File
import kotlin.math.log

class ProfileConfiguration : AppCompatActivity() {

    private lateinit var binding: ActivityProfileConfigurationBinding

    private val selectSinglePhotoContract = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        // Handle the returned Uri
        uri?.let {it ->
            tempImageUri = it
            val imageView = binding.profileImage
            imageView.setImageURI(it)
        }
    }

    private val takePhotoContract = registerForActivityResult(ActivityResultContracts.TakePicture()) {

        if (it) {
        // Handle the photo uri
            val imageView = binding.profileImage
            imageView.setImageURI(tempImageUri)
        }
    }

    private var tempImageUri: Uri? = null

    // Gestion de permisos
    // Permiso Cámara
    private var cameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            tempImageUri = initTempUri()
            takePhotoContract.launch(tempImageUri)
        }
    }

    private var galleryPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            selectSinglePhotoContract.launch(PickVisualMediaRequest())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileConfigurationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()
    }

    override fun onResume() {
        super.onResume()
        if (!userGlobal.imageUrl.equals("")) {
            setUpPlayerInformation()
        }
    }

    private fun setUpPlayerInformation() {
        binding.profileImage.setImageURI(userGlobal.imageUrl.toUri())
    }

    private fun checkGalleryPermission() {
        galleryPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun checkCameraPermission() {
        cameraPermission.launch(android.Manifest.permission.CAMERA)
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

        binding.BtnTakePhoto.setOnClickListener {
            checkCameraPermission()
        }

        binding.BtnUploadPhoto.setOnClickListener {
            checkGalleryPermission()
        }

        binding.buttonSave.setOnClickListener {
            userGlobal.imageUrl = tempImageUri.toString()
            userGlobal.about = binding.editAbout.text.toString()
            userGlobal.age = binding.editAge.text.toString()
            Toast.makeText(this, "Configuration saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initTempUri(): Uri? {

        val tempImagesDir = File(
            applicationContext.filesDir, //this function gets the external cache dir
            getString(R.string.temp_images_dir)) //gets the directory for the temporary images dir

        tempImagesDir.mkdir() //Create the temp_images dir

        //Creates the temp_image.jpg file
        val tempImage = File(
            tempImagesDir, //prefix the new abstract path with the temporary images dir path
            getString(R.string.temp_image)) //gets the abstract temp_image file name

        //Returns the Uri object to be used with ActivityResultLauncher
        return FileProvider.getUriForFile(
            applicationContext,
            getString(R.string.authorities),
            tempImage)
    }
}