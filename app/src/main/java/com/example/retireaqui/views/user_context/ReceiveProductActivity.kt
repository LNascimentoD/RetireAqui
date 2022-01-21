package com.example.retireaqui.views.user_context

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.retireaqui.R
import com.example.retireaqui.network.models.Place
import com.example.retireaqui.network.models.Schedule
import com.example.retireaqui.network.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ReceiveProductActivity : AppCompatActivity() {
    var auth = Firebase.auth
    var database = Firebase.firestore

    lateinit var place: Place
    lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_product)

        id = intent.getStringExtra("id").toString()

        getScheduleInfo()
    }

    private fun getScheduleInfo(){
        database.collection("schedule")
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                var time = document.data?.get("time").toString()
                var date = document.data?.get("date").toString()
                var user_email = document.data?.get("user_email").toString()
                var product_id = document.data?.get("product_id").toString()

                val schedule = Schedule(time, date, user_email, product_id)
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }
}