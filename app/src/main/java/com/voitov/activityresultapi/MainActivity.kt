package com.voitov.activityresultapi

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
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

        val contractText = ActivityResultContracts.StartActivityForResult()
        val contractImage = ActivityResultContracts.GetContent()

        val contractTextLauncher = registerForActivityResult(contractText) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                val name = activityResult.data?.getStringExtra(UserNameActivity.EXTRA_NAME)
                if (name.isNullOrBlank()) {
                    return@registerForActivityResult
                }
                textViewUserName.text = name
            }
        }

        val contractImageLauncher = registerForActivityResult(contractImage) { it ->
            it?.let { uri ->
                imageViewGalleryImage.setImageURI(uri)
            }
        }

        buttonGetUserName.setOnClickListener {
            contractTextLauncher.launch(UserNameActivity.newIntent(this))
        }

        buttonGetImage.setOnClickListener {
            contractImageLauncher.launch("image/*")
        }
    }

    private fun initViews() {
        buttonGetUserName = findViewById(R.id.buttonGetUserName)
        textViewUserName = findViewById(R.id.textViewUserName)
        buttonGetImage = findViewById(R.id.buttonGetImage)
        imageViewGalleryImage = findViewById(R.id.imageViewGalleryImage)
    }
}