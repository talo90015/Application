package com.talo.application


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(v: View): RecyclerView.ViewHolder(v){
    var ph_name = v.findViewById<TextView>(R.id.txt_name)
    var ph_phone = v.findViewById<TextView>(R.id.txt_phone)
    var ph_delete = v.findViewById<ImageView>(R.id.img_delete)
}

class RecycleAdapter(private val data: ArrayList<RecycleViewActivity.Contact>) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount() = data.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ph_name.text = data[position].name
        holder.ph_phone.text = data[position].phone
        holder.ph_delete.setOnClickListener {
            data.removeAt(position)
            notifyDataSetChanged()
        }
    }



}
