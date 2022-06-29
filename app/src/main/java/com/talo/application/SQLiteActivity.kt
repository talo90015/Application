package com.talo.application

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.talo.application.databinding.ActivitySqliteBinding
import java.lang.Exception

class SQLiteActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySqliteBinding

    private var items: ArrayList<String> = ArrayList()
    private lateinit var dbrw: SQLiteDatabase
    private lateinit var adapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite)
        binding = ActivitySqliteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbrw = MyDBHelper(this).writableDatabase
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        binding.bookList.adapter = adapter
        setListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        dbrw.close()
    }

    private fun setListener() {
        binding.btnInsert.setOnClickListener {
            if (binding.editBook.length() < 1 || binding.editCost.length() < 1)
                showToast("請勿空白")
            else
                try {
                    dbrw.execSQL("INSERT INTO isDatabase (book, price) VALUES(?, ?)",
                        arrayOf(binding.editBook.text.toString(),
                            binding.editCost.text.toString()))
                }catch (e: Exception){
                    showToast("新增失敗 $e")
                }
        }
        binding.btnUpdate.setOnClickListener {
            if (binding.editBook.length() < 1 || binding.editCost.length() < 1)
                showToast("欄位請勿空白")
            else
                try {
                    dbrw.execSQL("UPDATE isDatabase SET price = '${binding.editCost.text}' where book like '${binding.editBook.text}'")
                    showToast("更新 ${binding.editBook.text}, 價格${binding.editCost.text}")
                    clean()
                }catch (e: Exception){
                    showToast("更新失敗 $e")
                }
        }
        binding.btnDelete.setOnClickListener {
            if (binding.editBook.length() < 1)
                showToast("請勿空白")
            else
                try {
                    dbrw.execSQL("delete from isDatabase where book like '${binding.editBook.text}'")
                    showToast("刪除 ${binding.editBook.text}")
                    clean()
                }catch (e: Exception){
                    showToast("刪除失敗 $e")
                }
        }
        binding.btnQuery.setOnClickListener {
            val queryString = if (binding.editBook.length() < 1)
                "SELECT * FROM isDatabase"
            else
                "SELECT * FROM isDatabase where book like '${binding.editBook.text}'"
            val c = dbrw.rawQuery(queryString, null)
            c.moveToFirst()
            items.clear()
            showToast("共有 ${c.count} 筆資料")
            for(i in 0 until c.count){
                items.add("書名 ${c.getString(0)}\t\t\t 書名 ${c.getInt(1)}")
                c.moveToNext()
            }
            adapter.notifyDataSetChanged()
            c.close()
        }
    }
    private fun showToast(text: String){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
    private  fun clean(){
        binding.editBook.setText("")
        binding.editCost.setText("")

    }
}