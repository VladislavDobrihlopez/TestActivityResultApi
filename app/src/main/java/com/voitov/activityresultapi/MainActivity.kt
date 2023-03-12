package com.voitov.activityresultapi

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
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

        val contractText = object : ActivityResultContract<Intent, String?>() {
            override fun createIntent(context: Context, input: Intent): Intent {
                return input
            }

            override fun parseResult(resultCode: Int, intent: Intent?): String? {
                return if (resultCode == RESULT_OK) {
                    intent?.getStringExtra(UserNameActivity.EXTRA_NAME)
                } else {
                    null
                }
            }
        }

        val contractImage = object : ActivityResultContract<Intent, Uri?>() {
            override fun createIntent(context: Context, input: Intent): Intent {
                return input.apply {
                    type = "image/*"
                }
            }

            override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
                return if (resultCode == RESULT_OK) {
                    intent?.data
                } else {
                    null
                }
            }
        }

        val contractTextLauncher = registerForActivityResult(contractText) { name ->
            if (name.isNullOrBlank()) {
                return@registerForActivityResult
            }
            textViewUserName.text = name
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
            contractImageLauncher.launch(Intent(Intent.ACTION_PICK))
        }
    }

    private fun initViews() {
        buttonGetUserName = findViewById(R.id.buttonGetUserName)
        textViewUserName = findViewById(R.id.textViewUserName)
        buttonGetImage = findViewById(R.id.buttonGetImage)
        imageViewGalleryImage = findViewById(R.id.imageViewGalleryImage)
    }
}