package com.example.retireaqui.views.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.retireaqui.R
import com.example.retireaqui.network.models.Schedule

class ScheduleAdapter(private val context: Activity, private val arrayList: ArrayList<Schedule>, private val arrayId: ArrayList<String>)
    : ArrayAdapter<Schedule>(context, R.layout.list_item_shop, arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.schedule_item_shop, null)

        val schedule = arrayList[position]
        val scheduleDate: TextView = view.findViewById(R.id.schedule_date)
        scheduleDate.text = "Data: " + schedule.time

        val scheduleHour: TextView = view.findViewById(R.id.schedule_hour)
        scheduleHour.text = "Hora: " + schedule.date

        return view
    }
}