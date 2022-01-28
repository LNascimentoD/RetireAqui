package com.example.retireaqui.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.retireaqui.R
import com.example.retireaqui.network.services.AuthService
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    var auth = AuthService()
    var database = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_register)

        onRegister()
    }

    private fun onRegister() {
        val editName: EditText = findViewById(R.id.login_email_value)
        val editEmail: EditText = findViewById(R.id.register_email_value)
        val editPassword: EditText = findViewById(R.id.login_password_value)
        val editType: CheckBox = findViewById(R.id.register_type_checkbox)

        val name = editName.getText()
        val email = editEmail.getText()
        val password = editPassword.getText()

        val btnOnRegister = findViewById<Button>(R.id.register_button)
        btnOnRegister.setOnClickListener {
            if(editType.isChecked){
                auth.signUp(name.toString(), email.toString(), password.toString(), "gerente") { result ->
                    if(result){
                        val activityHome = Intent(this, HomeActivity::class.java)
                        startActivity(activityHome)
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "deu ruim",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }else{
                auth.signUp(name.toString(), email.toString(), password.toString(), "cliente") { result ->
                    if(result){
                        val activityHome = Intent(this, HomeActivity::class.java)
                        startActivity(activityHome)
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "deu ruim",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}