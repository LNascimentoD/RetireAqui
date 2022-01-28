package com.example.retireaqui

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retireaqui.network.services.AuthService
import com.example.retireaqui.views.HomeActivity
import com.example.retireaqui.views.RegisterActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    var auth = AuthService()
    var database = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        onClickLoginButton ()
        onClickLoginButtonLegendLink ()
    }

    public override fun onStart() {
        super.onStart()

    }

    private fun onClickLoginButton (){
        val editEmail : EditText = findViewById(R.id.login_email_value)
        val editPassword : EditText = findViewById(R.id.login_password_value)

        val email = editEmail.getText()
        val password = editPassword.getText()

        val btnNavigationRegister: Button = findViewById(R.id.login_button)
        btnNavigationRegister.setOnClickListener{
            auth.signIn(email.toString(), password.toString()) { result ->
                if(result){
                    val activityHome = Intent(this, HomeActivity::class.java)
                    startActivity(activityHome)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Algo deu errado na autenticação",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun onClickLoginButtonLegendLink (){
        val btnNavigationRegister: TextView = findViewById(R.id.login_button_legend_link)
        btnNavigationRegister.setOnClickListener{
            val activityRegister = Intent(this, RegisterActivity::class.java)
            startActivity(activityRegister)
        }
    }
}