package com.example.retireaqui

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import com.example.retireaqui.network.models.User
import com.example.retireaqui.network.services.UserService

class RegisterActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_register)
    }

    private fun onRegister(){
        val editName : EditText = findViewById(R.id.register_name_value)
        val editEmail : EditText = findViewById(R.id.register_email_value)
        val editPassword : EditText = findViewById(R.id.register_password_value)

        val nome = editName.getText().toString()
        val email = editEmail.getText().toString()
        val password = editPassword.getText().toString()

        val btnOnRegister: Button = findViewById(R.id.register_button)
        btnOnRegister.setOnClickListener{
            Log.d(ContentValues.TAG, "Botão clicado!")
        }
    }
}