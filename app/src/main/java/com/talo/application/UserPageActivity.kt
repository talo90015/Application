package com.talo.application

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.talo.application.databinding.ActivityUserPageBinding

class UserPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_page)
        binding = ActivityUserPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener {
            when{
                binding.editUser.length() < 1 ->
                    Toast.makeText(this, "輸入姓名", Toast.LENGTH_SHORT).show()
                binding.editPhone.length() < 1 ->
                    Toast.makeText(this, "輸入電話", Toast.LENGTH_SHORT).show()
                else ->{
                    val bundle =  Bundle()
                    bundle.putString("name", binding.editUser.text.toString())
                    bundle.putString("phone", binding.editPhone.text.toString())
                    setResult(Activity.RESULT_OK, Intent().putExtras(bundle))
                    finish()
                }
            }
        }
    }
}