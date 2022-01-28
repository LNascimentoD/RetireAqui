package com.example.retireaqui.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.retireaqui.MainActivity
import com.example.retireaqui.R
import com.example.retireaqui.network.models.User
import com.example.retireaqui.network.services.AuthService
import com.example.retireaqui.network.services.UserService
import com.example.retireaqui.views.manager_context.ListShopActivity
import com.example.retireaqui.views.user_context.ListScheduleActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    var auth = Firebase.auth
    var database = Firebase.firestore
    var userService = UserService()
    var authService = AuthService()

    lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_home)

        email = auth.currentUser?.email.toString()

        getUserInfo()
        toMap()
        toShop()
        toSchedule()
        logoff()
    }

    private fun getUserInfo(){
        userService.getUser(email){ user ->
            val user_name_textView: TextView = findViewById(R.id.home_user)
            user_name_textView.setText(user.name)

            if(user.type == "gerente"){
                val btnNavigationToMap: View = findViewById(R.id.to_schedule)
                btnNavigationToMap.isVisible = false
            }
        }
    }

    private fun toMap(){
        val btnNavigationToMap: View = findViewById(R.id.to_map)
        btnNavigationToMap.setOnClickListener{
            val activityMap = Intent(this, MapActivity::class.java)

            activityMap.putExtra("lat", "100.0")
            activityMap.putExtra("long", "100.0")

            startActivity(activityMap)
        }
    }

    private fun toShop(){
        val btnNavigationToMap: View = findViewById(R.id.to_shop)
        btnNavigationToMap.setOnClickListener{
            val activityListShop = Intent(this, ListShopActivity::class.java)

            startActivity(activityListShop)
        }
    }

    private fun toSchedule(){
        val btnNavigationToMap: View = findViewById(R.id.to_schedule)
        btnNavigationToMap.setOnClickListener{
            val activityListSchedule = Intent(this, ListScheduleActivity::class.java)

            startActivity(activityListSchedule)
        }
    }

    private fun logoff(){
        val btnNavigationToMap: View = findViewById(R.id.logoff_button)
        btnNavigationToMap.setOnClickListener{
            authService.logout()

            val activityLogin = Intent(this, MainActivity::class.java)
            startActivity(activityLogin)
        }
    }
}