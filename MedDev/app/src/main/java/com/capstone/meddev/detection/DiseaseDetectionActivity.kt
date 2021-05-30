package com.capstone.meddev.detection

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.capstone.meddev.dashboard.TFLiteHelper
import com.capstone.meddev.databinding.ActivityDiseaseDetectionBinding
import java.io.IOException

class DiseaseDetectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiseaseDetectionBinding
    private lateinit var imageUri: Uri
    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiseaseDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tfLiteHelper = TFLiteHelper(this)
        tfLiteHelper.init()

        binding.btnUploadPhoto.setOnClickListener {
            val selectType = "image/*"
            val selectPicture = "Select Picture"

            val intent = Intent()
            intent.type = selectType
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, selectPicture), 12)
        }

        binding.btnDetection.setOnClickListener {
            tfLiteHelper.classifyImage(bitmap)
            setResult(tfLiteHelper.showResult()!!)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 12 && resultCode == RESULT_OK && data != null) {
            imageUri = data.data!!
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                binding.imgDisease.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun setResult(entries: List<String>) {
        binding.tvResult.text = ""
        for (entry in entries) {
            binding.tvResult.append(entry)
        }
    }
}