package com.example.retireaqui.views

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.retireaqui.R
import com.example.retireaqui.network.models.User
import com.example.retireaqui.views.manager_context.ListShopActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    var auth = Firebase.auth
    var database = Firebase.firestore

    lateinit var user: User
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
    }

    private fun getUserInfo(){
        database.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var id = auth.currentUser?.uid.toString()
                    var name = document.data["name"].toString()
                    var email = document.data["email"].toString()
                    var type = document.data["type"].toString()

                    user = User(id, name, email, type)
                }
                val user_name_textView: TextView = findViewById(R.id.home_user)
                user_name_textView.setText(user.name)
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
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
}