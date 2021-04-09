package com.example.save_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import android.Manifest
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fun onlick_save_image(view: View) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)
            }
            else
            {
                SaveImageToStorage()
            }
        }

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 100)
        {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                SaveImageToStorage()
            }
            else
            {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    public fun SaveImageToStorage(){
        val externalStorageState = Environment.getExternalStorageState()
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED))
        {
            val storageDirectory = Environment.getExternalStorageDirectory().toString()
            val file = File(storageDirectory, "test.jpg")
            try
            {
                val stream:OutputStream = FileOutputStream(file)
                val drawable = ContextCompat.getDrawable(applicationContext, R.drawable.rakim)
                var bitmap = (drawable as BitmapDrawable).bitmap
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream.flush()
                Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show()
            }
            catch(e: Exception)
            {
                e.printStackTrace()
            }
        }
        else
        {
            Toast.makeText(this, "Unable to access the storage", Toast.LENGTH_SHORT).show()
        }
    }

}