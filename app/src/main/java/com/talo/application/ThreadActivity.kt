package com.talo.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.provider.Settings
import android.widget.Toast
import com.talo.application.databinding.ActivityThirdBinding
import com.talo.application.databinding.ActivityThreadBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ThreadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThreadBinding
    private var progressRabbit = 0
    private var progressTurtle = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)
        binding = ActivityThreadBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
    private fun btnStart(){
        binding.btnStart.setOnClickListener {
            binding.btnStart.isEnabled = false
            progressRabbit = 0
            progressTurtle = 0

            runRabbit()
            runTurtle()
        }
    }
    private val handler = Handler(Looper.getMainLooper()){ msg ->
        if (msg.what == 1){
            binding.seekBarRabbit.progress = progressRabbit
        }
        if (progressRabbit >= 100 && progressTurtle < 100){
            Toast.makeText(this, "兔子勝利", Toast.LENGTH_SHORT).show()
            binding.btnStart.isEnabled = true
        }
        true
    }

    private fun runRabbit() {
        Thread{
            val sleepProbability = arrayOf(true, false, false)
            while (progressRabbit < 100 && progressTurtle < 100){
                try {
                    Thread.sleep(1000)
                    if (sleepProbability.random())
                        Thread.sleep(300)
                }catch (e: InterruptedException){
                    e.printStackTrace()
                }
                progressRabbit += 3
                val msg = Message()
                msg.what = 1
                handler.sendMessage(msg)
            }
        }.start()
    }

    private fun runTurtle() {
        GlobalScope.launch(Dispatchers.Main) {
            while (progressTurtle < 100 && progressRabbit < 100){
                delay(300)
                progressTurtle += 1
                binding.seekBarTurtle.progress = progressTurtle
                if (progressTurtle >= 100 && progressRabbit < 100){
                    Toast.makeText(this@ThreadActivity, "烏龜勝利", Toast.LENGTH_SHORT).show()
                    binding.btnStart.isEnabled = true
                }
            }
        }
    }
}