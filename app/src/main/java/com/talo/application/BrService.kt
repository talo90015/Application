package com.talo.application

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.lang.Exception
import kotlin.concurrent.thread

class BrService : Service() {
    private var channel = ""
    private lateinit var thread: Thread

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.extras?.let {
            channel = it.getString("channel", "")
        }
        broadcast(
            when(channel){
                "music" -> "音樂"
                "news" -> "新聞"
                "sport" -> "體育"
                else -> "....."
            }
        )
        if (::thread.isInitialized && thread.isAlive)
            thread.interrupt()
        thread = Thread{
            try {
                Thread.sleep(3000)
                broadcast(
                    when(channel){
                        "music" -> "音樂"
                        "news" -> "新聞"
                        "sport" -> "體育"
                        else -> "....."
                    }
                )
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        thread.start()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null
    private fun broadcast(msg: String) =
        sendBroadcast(Intent(channel).putExtra("msg", msg))
}