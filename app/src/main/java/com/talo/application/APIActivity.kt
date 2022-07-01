package com.talo.application



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import com.talo.application.databinding.ActivityApiactivityBinding
import okhttp3.*
import java.io.IOException


class APIActivity : AppCompatActivity() {
    class MyJsonData {
        lateinit var result: Result
        class Result {
            lateinit var records: Array<Record>
            class Record {
                var SiteName = ""
                var Status = ""
            }
        }
    }
    private lateinit var binding: ActivityApiactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apiactivity)
        binding = ActivityApiactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSearch.setOnClickListener {
            binding.btnSearch.isEnabled = false
            btnRequest()
        }
    }

    private fun btnRequest() {
        val url = "https://api.italkutalk.com/api/air"
        val req = Request.Builder()
            .url(url)
            .build()
        OkHttpClient().newCall(req).enqueue(object : Callback{

            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                val myJsonData = Gson().fromJson(json, MyJsonData::class.java)
                showDialog(myJsonData)
            }
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    binding.btnSearch.isEnabled = true
                    Toast.makeText(this@APIActivity, "請求失敗 $e", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun showDialog(obj: MyJsonData){
        val items = arrayOfNulls<String>(obj.result.records.size)
        obj.result.records.forEachIndexed{index, data ->
            items[index] = "地區 ${data.SiteName}, 狀態 ${data.Status}"
        }
        runOnUiThread {
            binding.btnSearch.isEnabled = true
            AlertDialog.Builder(this@APIActivity)
                .setTitle("空氣品質")
                .setItems(items, null)
                .show()

        }
    }
}
