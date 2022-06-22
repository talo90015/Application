 package com.talo.application

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.lang.Exception

 class IsService : Service() {

    override fun onCreate() {
        super.onCreate()
        Thread{
            try {
                Thread.sleep(3000)
                val intent = Intent(this, ServiceActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)

            }catch (e: Exception){
                e.printStackTrace()
            }
        }.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}