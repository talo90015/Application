package com.talo.application

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.talo.application.databinding.ActivityMessageBinding

class MessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMessageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        messageView()
    }

    private fun messageView() {

        val items = arrayOf("1", "2", "3")
        binding.btnToast.setOnClickListener {
            Toast.makeText(this, "A", Toast.LENGTH_SHORT).show()
        }
        binding.btnCustomToast.setOnClickListener {
            val toast = Toast(this)
            toast.setGravity(Gravity.BOTTOM, 0, 50)
            toast.duration = Toast.LENGTH_SHORT
            toast.view = layoutInflater.inflate(R.layout.custom_toast, null)
            toast.show()
        }
        binding.btnSnackbar.setOnClickListener {
            Snackbar.make(it, "snackBar", Snackbar.LENGTH_SHORT)
                .setAction("Btn") {
                    Toast.makeText(this, "snackBar", Toast.LENGTH_SHORT).show()
                }.show()
        }
        binding.btnAlertDialog.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("AlertDialog")
                .setMessage("Contact")
                .setNeutralButton("Left") { _, _ ->
                    Toast.makeText(this, "NeutralButton", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Center") { _, _ ->
                    Toast.makeText(this, "NeutralButton", Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("Right") { _, _ ->
                    Toast.makeText(this, "PositiveButton", Toast.LENGTH_SHORT).show()
                }.show()
        }
        binding.btnListAlertDialog.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("List AlertDialog")
                .setItems(items) { _, i ->
                    Toast.makeText(this, "第${items[i]}個", Toast.LENGTH_SHORT).show()
                }.show()
        }
        binding.btnRbAlertdialog.setOnClickListener {
            var postion = 0
            AlertDialog.Builder(this)
                .setTitle("List AlertDialog")
                .setSingleChoiceItems(items, 0){_, i ->
                    postion = i
                }
                .setPositiveButton("cancel"){_, _ ->
                    Toast.makeText(this, "第${items[postion]}個", Toast.LENGTH_SHORT).show()
                }
                .show()
        }
    }
}
