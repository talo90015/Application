package com.talo.application

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.talo.application.databinding.ActivityRadioBinding


class RadioActivity : AppCompatActivity() {
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.extras?.let {
                binding.txtMsg.text = "${it.getString("msg")}"
            }
        }

    }
    private lateinit var binding: ActivityRadioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radio)
        binding = ActivityRadioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        broadcastReceiver()
    }

    private fun broadcastReceiver() {
        binding.btnMusic.setOnClickListener {
            register("music")
        }
        binding.btnNew.setOnClickListener {
            register("news")
        }
        binding.btnSport.setOnClickListener {
            register("sport")
        }
    }
    private fun register(channel: String){
        val intentFilter = IntentFilter(channel)
        registerReceiver(receiver, intentFilter)
        val intent = Intent(this, BrService::class.java)
        startService(intent.putExtra("channel", channel))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}