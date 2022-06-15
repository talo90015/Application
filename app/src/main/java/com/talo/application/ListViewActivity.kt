package com.talo.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter

import com.talo.application.databinding.ActivityListViewBinding

data class Item(
    val photo: Int,
    val name: String,
    val price: Int
)
class ListViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)
        binding = ActivityListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val count = ArrayList<String>()
        val item = ArrayList<Item>()
        val priceRange = IntRange(10, 100)
        val array = resources.obtainTypedArray(R.array.image_list)
        for (i in 0 until array.length()){
            val photo = array.getResourceId(i, 0)
            val name = "水果${i + 1}"
            val price = priceRange.random()
            count.add("${i+1}個")
            item.add(Item(photo, name, price))
        }
        array.recycle()
        binding.spinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, count)

        binding.gridView.numColumns = 3
        binding.gridView.adapter = MyAdapter(this, item, R.layout.listview_adapter2)
        binding.listView.adapter = MyAdapter(this, item, R.layout.listview_adapter)
    }

}

