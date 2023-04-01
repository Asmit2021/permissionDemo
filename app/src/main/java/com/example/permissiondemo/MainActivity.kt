package com.example.permissiondemo

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private val cameraResultLauncher:ActivityResultLauncher<String> =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()){
                    isGranted ->
                    if (isGranted){
                        Toast.makeText(
                            this,"Permission granted for camera.", Toast.LENGTH_SHORT).show()
                    }
                else{
                    Toast.makeText(this,"Permission is denied for camera.",Toast.LENGTH_SHORT).show()
                    }
                }
    private val cameraAndLocationResultLauncher:ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()){
            permissions ->
            permissions.entries.forEach {
                val permissionName = it.key
                val isGranted= it.value
                if(isGranted)
                {
                    if(permissionName == Manifest.permission.ACCESS_FINE_LOCATION){
                        Toast.makeText(
                            this,
                            "Permission granted for the location",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else if (permissionName == Manifest.permission.ACCESS_COARSE_LOCATION) {
                        Toast.makeText(
                            this,
                            "Access granted for the coarse location",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else{
                        Toast.makeText(
                            this,
                            "permission granted for the camera",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }else{
                    if(permissionName == Manifest.permission.ACCESS_FINE_LOCATION){
                        Toast.makeText(this,
                        "Permission denied for the location",
                        Toast.LENGTH_SHORT
                        ).show()
                    }
                    else if (permissionName == Manifest.permission.ACCESS_COARSE_LOCATION){
                        Toast.makeText(
                            this,
                            "Access denied for the coarse location",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else{
                        Toast.makeText(
                            this,
                        "Permission denied for the camera",
                        Toast.LENGTH_SHORT
                        )
                    }
                }
            }
        }

    private fun showRationaleDialog(title: String, message: String){
        val builder: AlertDialog.Builder= AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel"){dialog,_->
                dialog.dismiss()
            }
        builder.create().show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btnCameraPermission:Button? =null
        btnCameraPermission=findViewById(R.id.btnCameraPermission)
        btnCameraPermission?.setOnClickListener{
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M&&shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)){
                showRationaleDialog("Permission Demo requires camera access",
                "Camera cannot be used because access is denied")
            }else{
                cameraAndLocationResultLauncher.launch(
                    arrayOf(Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                )
            }
        }
    }
}