package com.example.retireaqui

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.retireaqui.network.models.Authentication
import com.example.retireaqui.views.MapActivity
import com.example.retireaqui.views.ListShopActivity
import com.example.retireaqui.views.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        onClickLoginButton ()
        onClickLoginButtonLegendLink ()
    }

    public override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if(currentUser != null){

        }
    }

    private fun onClickLoginButton (){
        val editEmail : EditText = findViewById(R.id.login_email_value)
        val editPassword : EditText = findViewById(R.id.login_password_value)

        val email = editEmail.getText()
        val password = editPassword.getText()


        val btnNavigationRegister: Button = findViewById(R.id.login_button)
        btnNavigationRegister.setOnClickListener{
            //Authentication.logout()

            Log.d(ContentValues.TAG, "Deu ruim: " + auth.currentUser)

            Authentication.signIn(email.toString(), password.toString())

            Log.d(ContentValues.TAG, "Deu Bom: " + auth.currentUser)

            if(auth.currentUser != null){
                val activityMap = Intent(this, MapActivity::class.java)
                startActivity(activityMap)

                //val listShopActivity = Intent(this, ListShopActivity::class.java)
                //startActivity(listShopActivity)
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