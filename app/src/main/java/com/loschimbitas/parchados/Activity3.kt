package com.loschimbitas.parchados

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Activity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)

        val boton1: Button = findViewById(R.id.button1)
        boton1.setOnClickListener {
            val intent = Intent(this, Activity9::class.java)
            startActivity(intent)
        }
        val boton2: Button = findViewById(R.id.button2)
        boton2.setOnClickListener {
            val intent = Intent(this, Activity4::class.java)
            startActivity(intent)
        }

        val boton3: Button = findViewById(R.id.button3)
        boton3.setOnClickListener {
            val intent = Intent(this, Activity5::class.java)
            startActivity(intent)
        }

        checkPermission()
    }

    private fun openCamera() {
        // Abrir la cámara
        Toast.makeText(this, "Abriendo la cámara", Toast.LENGTH_SHORT).show()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Permiso no aceptado por el momento
            requestCameraPermission()
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 10)
        } else {
            //Tiene permiso
            openCamera()
        }
    }

    private fun requestCameraPermission () {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            // El usuario ha rechazado los permisos
        } else {
            // El código va asignado
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 10)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 10) { // Nuestros permisos
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso aceptado
                openCamera()
            } else {
                // Permiso denegado
                Toast.makeText(this, "Permiso denegado por primera vez", Toast.LENGTH_SHORT).show()
            }
        }
    }
}