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
import com.loschimbitas.parchados.Datos.Identificadores

class Activity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_4)

        val boton1: Button = findViewById(R.id.button1)
        boton1.setOnClickListener {
            val intent = Intent(this, Activity9::class.java)
            startActivity(intent)
        }
        val boton2: Button = findViewById(R.id.button2)
        boton2.setOnClickListener {
            val intent = Intent(this, Activity3::class.java)
            startActivity(intent)
        }

        init()
    }

    fun init(){
        // Chequear si tenemos el permiso de contactos
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Si no tenemos el permiso, pedirlo
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // Explicarle al usuario porque necesitamos el permiso
            }

            // Pedir el permiso insistentemente
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                Identificadores.PERMISOCAMARA
            )
        }
        onRequestPermissionsResult(
            Identificadores.PERMISOGPS,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            intArrayOf(PackageManager.PERMISSION_GRANTED)
        )

    }

    @Override
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

//        val Texto: TextView = findViewById(R.id.textViewPermiso)

        if (requestCode == Identificadores.PERMISOGPS)
            for (i in permissions.indices) {
                val permission = permissions[i]
                val grantResult = grantResults[i]
                if (permission == Manifest.permission.READ_CONTACTS)
                    if (grantResult == PackageManager.PERMISSION_GRANTED)
                        Toast.makeText(this, "PERMISO GPS CONCEDIDO", Toast.LENGTH_SHORT).show()
//                        Texto.text = "PERMISO CONCEDIDO"
                    else if (grantResult == PackageManager.PERMISSION_DENIED)
                        Toast.makeText(this, "PERMISO GPS DENEGADO", Toast.LENGTH_SHORT).show()
//                        Texto.text = "PERMISO DENEGADO"
            }

    }
}