package com.talo.application

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.drawToBitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.talo.application.databinding.ActivityPhotoBinding
import java.lang.Exception

class PhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoBinding
    private var angle = 0f

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == RESULT_OK) {
            val img = data?.extras?.get("data") ?: return
            val bitmap = img as Bitmap
            binding.imgCamara.setImageBitmap(img as Bitmap)
            recognizeImage(bitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        binding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        photoPicture()
        goService()
    }

    private fun photoPicture() {
        binding.btnPhoto.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(intent, 0)
            } catch (e: Exception) {
                Toast.makeText(this, "無相機", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnRotate.setOnClickListener {
            angle += 90f
            binding.imgCamara.rotation = angle
            val bitmap = binding.imgCamara.drawToBitmap()
            recognizeImage(bitmap)
        }
    }

    private fun goService() {
        binding.btnService.setOnClickListener {
            startService(Intent(this, IsService::class.java))
            Toast.makeText(this, "啟動~~", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun recognizeImage(bitmap: Bitmap) {
        try {
            val labeler = ImageLabeling.getClient(
                ImageLabelerOptions.DEFAULT_OPTIONS
            )
            val inputImage = InputImage.fromBitmap(bitmap, 0)
            labeler.process(inputImage)
                .addOnSuccessListener { labels ->
                    val result = arrayListOf<String>()
                    for (label in labels) {
                        val text = label.text
                        val confidence = label.confidence
                        result.add("$text , 可信度$confidence")
                    }
                    binding.photoList.adapter =
                        ArrayAdapter(this, android.R.layout.simple_list_item_1, result)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "錯誤", Toast.LENGTH_SHORT).show()
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}