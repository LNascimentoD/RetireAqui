package com.example.retireaqui.views.adapters

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.retireaqui.R
import com.example.retireaqui.network.models.Place
import com.example.retireaqui.network.models.Product
import com.example.retireaqui.views.MapActivity

class ShopAdapter(private val context: Activity, private val arrayList: ArrayList<Place>, private val arrayId: ArrayList<String>)
    : ArrayAdapter<Place>(context, R.layout.list_item_shop, arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item_shop, null)

        val shop = arrayList[position]
        val shopName: TextView = view.findViewById(R.id.shop_name)

        shopName.text = shop.name

        return view
    }
}