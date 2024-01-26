package com.example.imagesharingapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private var imageURI: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pickImageButton: Button = findViewById(R.id.pickImageBtn)
        val shareImageButton: Button = findViewById(R.id.shareImageBtn)
         imageView = findViewById(R.id.imageView)

        pickImageButton.setOnClickListener {

            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 101)
        }
        shareImageButton.setOnClickListener {
            if(imageURI == null) {
                Toast.makeText(this,"Please pick an image from your storage", Toast.LENGTH_SHORT).show()
            } else {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageURI)
                shareIntent.type = "image/jpeg"
                startActivity(Intent.createChooser(shareIntent, "Share Image"))
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101 && resultCode == RESULT_OK) {
            imageView.setImageURI(data?.data)
            imageURI = data?.data
        }
    }
}