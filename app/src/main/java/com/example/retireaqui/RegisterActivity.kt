package com.example.retireaqui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_register)
    }
}