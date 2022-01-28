package com.example.retireaqui.views.manager_context

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.retireaqui.R
import com.example.retireaqui.network.services.ShopService
import com.example.retireaqui.network.services.UserService
import com.example.retireaqui.views.adapters.ShopAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ListShopActivity : AppCompatActivity() {
    var auth = Firebase.auth

    val shopService = ShopService()
    val userService = UserService()

    lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_list_shop)

        email = auth.currentUser?.email.toString()

        getUserInfo()
        onClickAddProductButton()
    }

    override fun onResume() {
        super.onResume()

        getUserInfo()
    }

    private fun getUserInfo(){
        userService.getUser(email){user ->
            val btnNavigationCreateShop: TextView = findViewById(R.id.add_place_button)

            if(user.type == "gerente"){
                getPlacesManager()
            }else{
                getPlacesUser()
                btnNavigationCreateShop.isVisible = false
            }
        }
    }

    private fun getPlacesManager(){
        shopService.getPlaceByEmail(auth.currentUser?.email.toString()){ places, ids ->
            val adapter = ShopAdapter(this, places, ids)
            val listView: ListView = findViewById(R.id.listView_places)
            listView.adapter = adapter

            listView.setOnItemClickListener { adapterView, view, itemPosition, l ->
                val activityShopDetail = Intent(this, ShopDetailActivity::class.java)
                activityShopDetail.putExtra("id", ids[itemPosition])
                startActivity(activityShopDetail)
            }
        }
    }

    private fun getPlacesUser(){
        shopService.getAllPlace(){places, ids ->
            val adapter = ShopAdapter(this, places, ids)
            val listView: ListView = findViewById(R.id.listView_places)
            listView.adapter = adapter

            listView.setOnItemClickListener { adapterView, view, itemPosition, l ->
                val activityShopDetail = Intent(this, ShopDetailActivity::class.java)
                activityShopDetail.putExtra("id", ids[itemPosition])
                startActivity(activityShopDetail)
            }
        }
    }

    private fun onClickAddProductButton (){
        val btnNavigationCreateShop: TextView = findViewById(R.id.add_place_button)
        btnNavigationCreateShop.setOnClickListener{
            val activityCreateShop = Intent(this, CreateShopActivity::class.java)
            activityCreateShop.putExtra("user_email", auth.currentUser?.email)

            startActivity(activityCreateShop)
        }
    }
}