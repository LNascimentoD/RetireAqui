package com.example.retireaqui.views.user_context

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ListView
import com.example.retireaqui.R
import com.example.retireaqui.network.services.ScheduleService
import com.example.retireaqui.views.adapters.ScheduleAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ListScheduleActivity : AppCompatActivity() {
    var auth = Firebase.auth
    val scheduleService = ScheduleService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_list_schedule)

        getSchedule()
    }

    private fun getSchedule(){
        scheduleService.getAllSchedule(auth.currentUser?.email.toString()) { schedules, ids ->
            val adapter = ScheduleAdapter(this, schedules, ids)
            val listView: ListView = findViewById(R.id.listView_places)
            listView.adapter = adapter

            listView.setOnItemClickListener { adapterView, view, itemPosition, l ->
                val activityReceiveProduct = Intent(this, ReceiveProductActivity::class.java)
                activityReceiveProduct.putExtra("id", ids[itemPosition])
                startActivity(activityReceiveProduct)
            }
        }
    }
}