package com.talo.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.talo.application.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rpsGame()
    }

    private fun rpsGame() {
        binding.btnResult.setOnClickListener {
            if (binding.editName.length() < 1) {
                Toast.makeText(this, "Input Name!!", Toast.LENGTH_SHORT).show()
            }
            val computer = (Math.random() * 3).toInt()  //亂數結果
            Toast.makeText(this, "$computer", Toast.LENGTH_SHORT).show()


            when {
                    binding.rbScissor.isChecked && computer == 2 ||
                    binding.rbRock.isChecked && computer == 0 ||
                    binding.rbPaper.isChecked && computer == 1 -> {
                binding.txtCommit.text = "You Win"
                }
                    binding.rbScissor.isChecked && computer == 1 ||
                    binding.rbRock.isChecked && computer == 2 ||
                    binding.rbPaper.isChecked && computer == 0 -> {
                binding.txtCommit.text = "You Lose"
                }
                else -> binding.txtCommit.text = "Draw"
            }
        }
    }
}