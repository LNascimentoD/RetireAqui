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
import com.example.retireaqui.network.models.Product
import com.example.retireaqui.views.RegisterActivity

class ProductsAdapter(private val context: Activity, private val arrayList: ArrayList<Product>, private val arrayId: ArrayList<String>)
    : ArrayAdapter<Product>(context, R.layout.list_item_product, arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item_product, null)

        val product = arrayList[position]
        val productName: TextView = view.findViewById(R.id.product_name)
        val productEntrega: TextView = view.findViewById(R.id.entregar_button)

        productName.text = product.name

        productEntrega.setOnClickListener{
            Log.w(ContentValues.TAG, "2222222 ${arrayId[position]}")
        }

        return view
    }
}