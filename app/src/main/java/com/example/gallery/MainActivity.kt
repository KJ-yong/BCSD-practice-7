package com.example.gallery

import android.Manifest
import android.content.ContentUris
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.example.gallery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var adapter = GalleryAdapter(this)
    private val projection = arrayOf(
        MediaStore.Images.Media._ID
    )
    private val storagePermissionContract = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(it) loadImage()
        else Toast.makeText(this,R.string.non_permission,Toast.LENGTH_SHORT)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.recyclerView.adapter = adapter
        storagePermissionContract.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
    private fun loadImage(){
        contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
        )?.use{cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            while(cursor.moveToNext()){
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