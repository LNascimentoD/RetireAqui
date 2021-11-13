package com.example.retireaqui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import com.example.retireaqui.R
import com.example.retireaqui.network.models.User
import com.example.retireaqui.network.services.UserService

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_register)

        onRegister()
    }

    private fun onRegister(){
        val editName : EditText = findViewById(R.id.register_name_value)
        val editEmail : EditText = findViewById(R.id.register_email_value)
        val editPassword : EditText = findViewById(R.id.register_password_value)

        val name = editName.getText()
        val email = editEmail.getText()
        val password = editPassword.getText()

        val btnOnRegister = findViewById<Button>(R.id.register_button)
        btnOnRegister.setOnClickListener{
            val user = User(name.toString(), email.toString(), password.toString())
            val userService = UserService()
            userService.registerUser(user)
        }
    }
}