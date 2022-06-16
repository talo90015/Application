package com.talo.application

import android.app.Activity
import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.talo.application.databinding.ActivityRecycleViewBinding

class RecycleViewActivity : AppCompatActivity() {

    data class Contact(
        val name: String,
        val phone: String
    )
    private lateinit var binding: ActivityRecycleViewBinding
    private lateinit var adapter: RecycleAdapter
    private val contacts = ArrayList<Contact>()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.let {
            if (requestCode == 1 && resultCode == Activity.RESULT_OK){
                val name = it.getString("name") ?: return@let
                val photo = it.getString("phone") ?: return@let
                contacts.add(Contact(name, photo))
                adapter.notifyDataSetChanged()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)
        binding = ActivityRecycleViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recycleView.layoutManager = linearLayoutManager
        adapter = RecycleAdapter(contacts)
        binding.recycleView.adapter = adapter

        binding.btnAdd.setOnClickListener {
            startActivityForResult(Intent(this, UserPageActivity::class.java), 1)
        }
    }


}
