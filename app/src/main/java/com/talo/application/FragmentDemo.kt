package com.talo.application

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import androidx.fragment.app.FragmentPagerAdapter
import com.talo.application.databinding.ActivityFragmentDemoBinding

class FragmentDemo : AppCompatActivity() {

    private lateinit var binding: ActivityFragmentDemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_demo)
        binding = ActivityFragmentDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        binding.viewPage.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        Log.e("A", "Start")
    }

    override fun onResume() {
        super.onResume()
        Log.e("A", "Resume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("A", "Pause")
    }

    override fun onStop() {
        super.onStop()
        Log.e("A", "Stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("A", "Destroy")
    }
    class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm){
        override fun getCount(): Int = 2

        override fun getItem(position: Int) = when(position) {
            0 -> First()
            else -> Second()
        }

    }

}