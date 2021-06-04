package com.capstone.meddev.detection

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.meddev.R
import com.capstone.meddev.helper.TFLiteHelper
import com.capstone.meddev.databinding.ActivityDiseaseDetectionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

class DiseaseDetectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiseaseDetectionBinding
    private lateinit var imageUri: Uri
    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiseaseDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.deteksi_penyakit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

        binding.tvResult.text = getString(R.string.hasil_prediksi)

        binding.btnDetection.setOnClickListener {
            try {
                tfLiteHelper.classifyImage(bitmap)
                CoroutineScope(Dispatchers.Main).launch {
                    binding.tvResult.text = getString(R.string.prediksi_progres)
                    delay(2000L)
                    binding.tvLabel.visibility = View.VISIBLE
                    setResult(tfLiteHelper.showResult()!!)
                    if (binding.tvResult.text.toString() == "cataract") {
                        binding.tvResult.setOnClickListener {
                            startActivity(
                                Intent(
                                    this@DiseaseDetectionActivity,
                                    DiseaseDetailActivity::class.java
                                ).apply {
                                    putExtra(DiseaseDetailActivity.EXTRA_CATARACT, urlCataract)
                                })
                        }
                    } else if (binding.tvResult.text.toString() == "glaucoma") {
                        binding.tvResult.setOnClickListener {
                            startActivity(
                                Intent(
                                    this@DiseaseDetectionActivity,
                                    DiseaseDetailActivity::class.java
                                ).apply {
                                    putExtra(DiseaseDetailActivity.EXTRA_GLAUCOMA, urlGlaucoma)
                                })
                        }
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Unggah foto mata terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val urlCataract = "https://www.halodoc.com/kesehatan/katarak"
        const val urlGlaucoma = "https://www.halodoc.com/kesehatan/glaukoma"
    }
}