package com.example.adminpkart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class adapter_bom( val requireContext: Context, val list_bom: ArrayList<model_bom>) :RecyclerView.Adapter<adapter_bom.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val imageView = itemView.findViewById<ImageView>(R.id.item_slider_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_slider, parent, false)
        )
    }

    override fun getItemCount(): Int{
        return list_bom.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView
        Glide.with(requireContext).load(list_bom[position].img).into(holder.imageView)
    }
}