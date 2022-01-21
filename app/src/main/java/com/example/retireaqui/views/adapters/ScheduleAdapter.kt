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
        val view: View = inflater.inflate(R.layout.list_item_shop, null)

        val schedule = arrayList[position]
        val scheduleName: TextView = view.findViewById(R.id.shop_name)

        return view
    }
}