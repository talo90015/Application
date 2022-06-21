package com.talo.application

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.talo.application.databinding.ActivityPhotoBinding
import java.lang.Exception

class PhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoBinding
    private var angle = 0f

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == RESULT_OK){
            val img = data?.extras?.get("data") ?: return
            binding.imgCamara.setImageBitmap(img as Bitmap)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        binding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        photoPicture()
    }
    private fun photoPicture(){
        binding.btnPhoto.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(intent, 0)
            }catch (e: Exception){
                Toast.makeText(this, "無相機", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnRotate.setOnClickListener {
            angle += 90f
            binding.imgCamara.rotation = angle
        }
    }

}