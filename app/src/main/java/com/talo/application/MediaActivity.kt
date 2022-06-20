package com.talo.application

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.talo.application.databinding.ActivityMediaBinding
import java.io.File
import java.lang.Exception
import java.util.*

class MediaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediaBinding
    private val recorder = MediaRecorder()
    private val player = MediaPlayer()
    private lateinit var folder: File
    private var fileName = ""

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && requestCode == 0){
            val result = grantResults[0]
            if (result == PackageManager.PERMISSION_DENIED)
                finish()
            else{
                setFolder()
                setListener()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val permission = android.Manifest.permission.RECORD_AUDIO
        if (ActivityCompat.checkSelfPermission(this, permission)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(permission), 0)
        }else{
            setFolder()
            setListener()
        }
    }

    override fun onDestroy() {
        recorder.release()
        player.release()
        super.onDestroy()
    }
    private fun setFolder(){
        folder = File(filesDir.absolutePath+"/record")
        if (!folder.exists()){
            folder.mkdirs()
        }
    }
    private fun setListener(){
        binding.btnRecord.setOnClickListener {
            fileName = "${Calendar.getInstance().time.time}"
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            recorder.setOutputFile(File(folder, fileName).absolutePath)
            recorder.prepare()
            recorder.start()
            binding.txtView.text = "錄音中..."
            binding.btnRecord.isEnabled = false
            binding.btnStopRecord.isEnabled = true
            binding.btnPlayer.isEnabled = false
            binding.btnStopPlayer.isEnabled = false
        }
        binding.btnStopRecord.setOnClickListener {
            try {
                val file = File(folder, fileName)
                recorder.stop()
                binding.txtView.text = "已儲存至${file.absolutePath}"
                binding.btnRecord.isEnabled = true
                binding.btnStopRecord.isEnabled = false
                binding.btnPlayer.isEnabled = true
                binding.btnStopPlayer.isEnabled = false
            }catch (e: Exception){
                e.printStackTrace()
                recorder.reset()
                binding.txtView.text = "錄製失敗"
                binding.btnRecord.isEnabled = true
                binding.btnStopRecord.isEnabled = false
                binding.btnPlayer.isEnabled = false
                binding.btnStopPlayer.isEnabled = false
            }
        }
        binding.btnPlayer.setOnClickListener {
            val file = File(folder, fileName)
            player.setDataSource(applicationContext, Uri.fromFile(file))
            player.setVolume(2f, 2f)
            player.prepare()
            player.start()
            binding.txtView.text = "播放中"
            binding.btnRecord.isEnabled = false
            binding.btnStopRecord.isEnabled = false
            binding.btnPlayer.isEnabled = false
            binding.btnStopPlayer.isEnabled = true
        }
        binding.btnStopPlayer.setOnClickListener {
            player.stop()
            player.reset()
            binding.txtView.text = "播放結束"
            binding.btnRecord.isEnabled = true
            binding.btnStopRecord.isEnabled = false
            binding.btnPlayer.isEnabled = true
            binding.btnStopPlayer.isEnabled = false
        }
        player.setOnCompletionListener {
            it.reset()
            binding.txtView.text = "播放結束"
            binding.btnRecord.isEnabled = true
            binding.btnStopRecord.isEnabled = true
            binding.btnPlayer.isEnabled = true
            binding.btnStopPlayer.isEnabled = false
        }
    }
}