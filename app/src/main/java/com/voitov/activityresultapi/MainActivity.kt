package com.voitov.activityresultapi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var buttonGetUserName: Button
    private lateinit var textViewUserName: TextView
    private lateinit var buttonGetImage: TextView
    private lateinit var imageViewGalleryImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

        buttonGetUserName.setOnClickListener {
            startActivityForResult(
                UserNameActivity.newIntent(this),
                REQUEST_CODE_RETRIEVING_USERNAME
            )
        }

        buttonGetImage.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }, REQUEST_CODE_RETRIEVING_IMAGE)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_RETRIEVING_USERNAME -> {
                val name = data?.getStringExtra(UserNameActivity.EXTRA_NAME) ?: ""
                textViewUserName.text = name
            }
            REQUEST_CODE_RETRIEVING_IMAGE -> {
                imageViewGalleryImage.setImageURI(data?.data)
            }
        }
    }

    private fun initViews() {
        buttonGetUserName = findViewById(R.id.buttonGetUserName)
        textViewUserName = findViewById(R.id.textViewUserName)
        buttonGetImage = findViewById(R.id.buttonGetImage)
        imageViewGalleryImage = findViewById(R.id.imageViewGalleryImage)
    }

    companion object {
        const val REQUEST_CODE_RETRIEVING_USERNAME = 100
        const val REQUEST_CODE_RETRIEVING_IMAGE = 101
    }
}