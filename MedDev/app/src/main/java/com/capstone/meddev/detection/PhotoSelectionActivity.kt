package com.capstone.meddev.detection

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.meddev.databinding.ActivityPhotoSelectionBinding


class PhotoSelectionActivity : AppCompatActivity() {
    private val CAMERA_REQUEST = 1888
    private val MY_CAMERA_PERMISSION_CODE = 100

    private lateinit var binding : ActivityPhotoSelectionBinding

    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val captureButton: Button = binding.capturePhotoBtn
        val selectPhotoButton : Button = binding.uploadPhotoBtn

        captureButton.setOnClickListener{
            openCamera()
        }

        selectPhotoButton.setOnClickListener{
            openGallery()
        }
    }

    private fun openGallery(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    private fun openCamera(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    MY_CAMERA_PERMISSION_CODE
                )
            }
        } else {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show()
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            val photo = data?.extras!!["data"] as Bitmap?
            //pindah ke halaman konfirmasi beserta kirim data foto
        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            val photo = data?.extras!!["data"] as Bitmap?
            //pindah ke halaman konfirmasi beserta kirim data foto
        }
    }
}