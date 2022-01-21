package com.example.retireaqui.views.user_context

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.example.retireaqui.R
import com.example.retireaqui.network.models.Place
import com.example.retireaqui.network.models.Schedule
import com.example.retireaqui.views.adapters.ScheduleAdapter
import com.example.retireaqui.views.adapters.ShopAdapter
import com.example.retireaqui.views.manager_context.ShopDetailActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListScheduleActivity : AppCompatActivity() {
    var auth = Firebase.auth
    var database = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_schedule)

        getSchedule()
    }

    private fun getSchedule(){
        val listSchedule : ArrayList<Schedule> = ArrayList()
        val listScheduleId : ArrayList<String> = ArrayList()

        database.collection("schedule")
            .whereEqualTo("user_email", "lucaszf.ln@gmail.com")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var time = document.data["time"].toString()
                    var date = document.data["date"].toString()
                    var user_email = document.data["user_email"].toString()
                    var product_id = document.data["product_id"].toString()

                    val schedule = Schedule(time, date, user_email, product_id)

                    listSchedule.add(schedule)
                    listScheduleId.add(document.id)
                }

                val adapter = ScheduleAdapter(this, listSchedule, listScheduleId)
                val listView: ListView = findViewById(R.id.listView_places)
                listView.adapter = adapter

                listView.setOnItemClickListener { adapterView, view, itemPosition, l ->
                    val activityReceiveProduct = Intent(this, ReceiveProductActivity::class.java)
                    activityReceiveProduct.putExtra("id", listScheduleId[itemPosition])
                    startActivity(activityReceiveProduct)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }
}