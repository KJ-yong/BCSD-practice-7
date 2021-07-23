package com.example.gallery

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gallery.databinding.ImageRecyclerBinding

class GalleryAdapter(private val context : Context) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    private var datas = mutableListOf<Uri>()
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ImageRecyclerBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = datas.size
    inner class ViewHolder(private val binding : ImageRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : Uri){
            Glide.with(itemView.context)
                .load(item)
                .override(200)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(binding.imageView)
        }
    }
    fun addItem(item : Uri){
        datas.add(item)
        notifyItemInserted(itemCount - 1)
    }
}