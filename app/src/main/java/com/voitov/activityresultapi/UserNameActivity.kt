package com.voitov.activityresultapi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class UserNameActivity : AppCompatActivity() {
    private lateinit var editTextInputName: EditText
    private lateinit var buttonSaveName: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_name)
        initViews()

        buttonSaveName.setOnClickListener {
            val newName = editTextInputName.text.toString()
            saveUserName(newName)
            finish()
        }
    }

    private fun saveUserName(name: String) {
        Intent().apply {
            putExtra(EXTRA_NAME, name)
            setResult(RESULT_OK, this)
        }
    }

    private fun initViews() {
        editTextInputName = findViewById(R.id.editTextInputName)
        buttonSaveName = findViewById(R.id.buttonSaveName)
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        fun newIntent(context: Context): Intent {
            return Intent(context, UserNameActivity::class.java)
        }
    }
}