package com.example.gallery

import android.content.ContentUris
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.gallery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var adapter = GalleryAdapter(this)
    val projection = arrayOf(
        MediaStore.Images.Media._ID
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.recyclerView.adapter = adapter
        LoadImage()
    }
    fun LoadImage(){
        contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
        )?.use{cursor ->
            if(cursor==null) Log.d("ifnull",cursor.toString())
            else Log.d("nonifnull",cursor.count.toString())
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            while(cursor.moveToNext()){
                Log.d("test",cursor.count.toString())
                val id = cursor.getLong(idColumn)
                val contentUri : Uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                )
                Log.d("abc", "uri : $contentUri")
                adapter.addItem(contentUri)
            }
        }
    }
}