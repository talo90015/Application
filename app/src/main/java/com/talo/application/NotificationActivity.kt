package com.talo.application

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.RemoteMessage
import com.talo.application.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        notificationBtn()
    }
    private fun notificationBtn(){
        binding.btnNotification.setOnClickListener {
            val nm = NotificationManagerCompat.from(this)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val name = "今天好累"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel("talo", name, importance)
                nm.createNotificationChannel(channel)
            }
            val intent = Intent(this, NotificationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            val text = "MSG"
            val builder = NotificationCompat.Builder(this, "talo")
                .setSmallIcon(android.R.drawable.btn_star_big_on)
                .setContentTitle(text)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            nm.notify(0, builder.build())

        }
    }
}