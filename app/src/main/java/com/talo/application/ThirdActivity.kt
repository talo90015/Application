package com.talo.application

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.talo.application.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {
    lateinit var binding: ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        order()
    }
    private fun order(){
        binding.btnBackFoodSystem.setOnClickListener {
            if (binding.editFoodName.length() < 1){
                Toast.makeText(this, "Input Food!!", Toast.LENGTH_SHORT).show()
            }else{
                val bundle = Bundle()
                bundle.putString("drink", binding.rgDrink.findViewById<RadioButton>
                                        (binding.rgDrink.checkedRadioButtonId).text.toString())
                bundle.putString("food", binding.editFoodName.text.toString())
                setResult(Activity.RESULT_OK, Intent().putExtras(bundle))
                finish()
            }
        }
    }
}