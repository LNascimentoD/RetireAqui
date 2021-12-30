package com.example.retireaqui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import com.example.retireaqui.R
import com.example.retireaqui.network.models.Authentication
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    var auth = Firebase.auth
    var database = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_register)

        onRegister()
    }

    private fun onRegister(){
        val editName : EditText = findViewById(R.id.login_email_value)
        val editEmail : EditText = findViewById(R.id.register_email_value)
        val editPassword : EditText = findViewById(R.id.login_password_value)

        val name = editName.getText()
        val email = editEmail.getText()
        val password = editPassword.getText()

        val btnOnRegister = findViewById<Button>(R.id.register_button)
        btnOnRegister.setOnClickListener{
            Authentication.signUp(name.toString(), email.toString(), password.toString(), "gerente")
        }
    }
}