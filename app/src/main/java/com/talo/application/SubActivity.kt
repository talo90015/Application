package com.talo.application

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.talo.application.databinding.ActivitySubBinding
import java.lang.Exception

class SubActivity : AppCompatActivity() {
    private var items: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private val uri = Uri.parse("content://com.talo.Demo")
    private lateinit var binding: ActivitySubBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        binding.bookList.adapter = adapter
        setListener()
    }

    private fun setListener() {
        binding.btnInsert.setOnClickListener {
            if (binding.editBook.length() < 1 || binding.editCost.length() < 1)
                showToast("請勿空白")
            else {
                val values = ContentValues()
                values.put("book", binding.editBook.text.toString())
                values.put("price", binding.editCost.text.toString())
                val contentUri = contentResolver.insert(uri, values)
                if (contentUri != null) {
                    showToast("新增 ${binding.editBook.text}, 價格 ${binding.editCost.text}")
                    clean()
                }else
                    showToast("新增失敗")
            }
        }
        binding.btnUpdate.setOnClickListener {
            if (binding.editBook.length() < 1 || binding.editCost.length() < 1)
                showToast("欄位請勿空白")
            else{
                val values = ContentValues()
                values.put("price", binding.editCost.text.toString())
                val count = contentResolver.update(uri, values, binding.editBook.text.toString(), null)
                if (count > 0) {
                    showToast("更新 ${binding.editBook.text}, 價格 ${binding.editCost.text}")
                    clean()
                }else
                    showToast("更新失敗")
            }
        }
        binding.btnDelete.setOnClickListener {
            if (binding.editBook.length() < 1)
                showToast("請勿空白")
            else {
                val count = contentResolver.delete(uri, binding.editBook.text.toString(), null)
                if (count > 0){
                    showToast("刪除 ${binding.editBook.text}")
                    clean()
                }else
                    showToast("刪除失敗")
            }
        }
        binding.btnQuery.setOnClickListener {
            val selection = if (binding.editBook.text.isEmpty()) null else binding.editBook.text.toString()
            val c = contentResolver.query(uri, null, selection.toString(), null, null)
            c ?: return@setOnClickListener
            c.moveToFirst()
            items.clear()
            showToast("共有 ${c.count} 筆資料")
            for (i in 0 until c.count) {
                items.add("書名 ${c.getString(0)}\t\t\t 書名 ${c.getInt(1)}")
                c.moveToNext()
            }
            adapter.notifyDataSetChanged()
            c.close()
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun clean() {
        binding.editBook.setText("")
        binding.editCost.setText("")

    }

}