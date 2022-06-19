package com.talo.application

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import com.talo.application.databinding.ActivityAnimationBinding

class AnimationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        binding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        animFrame()
        animTween()
    }
    private fun animFrame(){
        binding.imageFrame.setBackgroundResource(R.drawable.loading_animation)
        binding.btnStartAnim.setOnClickListener {
            val anim = binding.imageFrame.background as AnimationDrawable
            anim.start()
        }
        binding.btnStopAnim.setOnClickListener {
            val anim = binding.imageFrame.background as AnimationDrawable
            anim.stop()
        }

    }
    private fun animTween(){

        binding.btnAlpha.setOnClickListener {
            val anim = AlphaAnimation(1.0f, 0.2f)
            anim.duration = 1000
            binding.imageTween.startAnimation(anim)
        }
        binding.btnScale.setOnClickListener {
            val anim = ScaleAnimation(1.0f, 3.0f, 1.0f, 3.0f)
            anim.duration = 1000
            binding.imageTween.startAnimation(anim)
        }
        binding.btnTranslate.setOnClickListener {
            val anim = TranslateAnimation(0f, 200f, 0f, 500f)
            anim.duration = 1000
            binding.imageTween.startAnimation(anim)
        }
        binding.btnRotate.setOnClickListener {
            val anim = RotateAnimation(0f, 360f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f)
            anim.duration = 500
            binding.imageTween.startAnimation(anim)
        }
    }
}