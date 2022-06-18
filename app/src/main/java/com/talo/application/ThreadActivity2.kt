package com.talo.application

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.talo.application.databinding.ActivityThread2Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.pow

class ThreadActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityThread2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread2)
        binding = ActivityThread2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        btnCalculate()
    }

    private fun btnCalculate() {

        binding.btnCalculate.setOnClickListener {
            when{
                binding.editHeight.length() < 1 -> Toast.makeText(this, "input", Toast.LENGTH_SHORT).show()
                binding.editWeight.length() < 1 -> Toast.makeText(this, "input", Toast.LENGTH_SHORT).show()
                binding.editAge.length() < 1 -> Toast.makeText(this, "input", Toast.LENGTH_SHORT).show()
                else->runCoroutines()
            }
        }
    }

    private fun runCoroutines() {
        binding.txtShowWeight.text = "標準體重\n無"
        binding.txtShowFat.text = "體脂肪\n無"
        binding.txtShowBmi.text = "Bmi\n無"

        binding.progressBar2.progress = 0
        binding.txtProgress.text = "0%"
        binding.llProgress.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.Main) {
            var progress = 0
            while (progress < 100){
                delay(50)
                binding.progressBar2.progress = progress
                binding.txtProgress.text = "$progress%"
                progress++
            }
            binding.llProgress.visibility = View.GONE

            val height = binding.editHeight.text.toString().toDouble()
            val weight = binding.editWeight.text.toString().toDouble()
            val age = binding.editAge.text.toString().toDouble()
            val bmi = weight / ((height / 100).pow(2))

            val (stand_weight, body_fat) = if (binding.rbBoy.isChecked){
                Pair((height - 80) * 0.7, 1.39 * bmi + 0.16 * age - 19.34)
            }else{
                Pair((height - 70) * 0.6, 1.39 * bmi + 0.16 * age - 9)
            }
            binding.txtShowWeight.text = "標準體重\n ${String.format("%.2f", stand_weight)}"
            binding.txtShowFat.text = "體脂肪\n ${String.format("%.2f", body_fat)}"
            binding.txtShowBmi.text = "Bmi\n ${String.format("%.2f", bmi)}"
        }
    }
}