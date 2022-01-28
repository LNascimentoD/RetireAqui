package com.example.retireaqui.views.manager_context

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.retireaqui.R
import com.example.retireaqui.network.models.Place
import com.example.retireaqui.network.models.User
import com.example.retireaqui.network.services.LocationService
import com.example.retireaqui.network.services.ProductService
import com.example.retireaqui.network.services.ShopService
import com.example.retireaqui.network.services.UserService
import com.example.retireaqui.views.MapActivity
import com.example.retireaqui.views.adapters.ProductsAdapter
import com.example.retireaqui.views.user_context.CreateScheduleActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ShopDetailActivity : AppCompatActivity() {
    var auth = Firebase.auth
    val userService = UserService()
    val shopService = ShopService()
    val productService = ProductService()
    val locationService = LocationService()

    lateinit var place: Place
    lateinit var id: String
    lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_shop_detail)

        id = intent.getStringExtra("id").toString()

        userService.getUser(auth.currentUser?.email.toString()) { user ->
            val btnNavigationCreateShop: TextView = findViewById(R.id.add_product_button)

            type = user.type

            if(type == "gerente"){
                showListViewManager()
            }else{
                btnNavigationCreateShop.isVisible = false
                showListViewClient()
            }
        }

        getPlaceInfo()

        onClickAddProductButton()
        onClickLocalizationNav ()
    }

    private fun showListViewManager(){
        productService.getProductByPlaceId(id) { products, ids ->
            val adapter = ProductsAdapter(this, products, ids)
            val listView: ListView = findViewById(R.id.listView)
            listView.adapter = adapter

            listView.setOnItemClickListener { adapterView, view, itemPosition, l ->
                if (type == "gerente") {
                    val activityDeliverProduct = Intent(this, DeliverProductActivity::class.java)
                    activityDeliverProduct.putExtra("id", ids[itemPosition])
                    startActivity(activityDeliverProduct)
                } else {
                    val activityCreateSchedule = Intent(this, CreateScheduleActivity::class.java)
                    activityCreateSchedule.putExtra("id", ids[itemPosition])
                    startActivity(activityCreateSchedule)
                }
            }
        }
    }

    private fun showListViewClient(){
        productService.getProductByPlaceIdAndUserEmail(auth.currentUser?.email.toString(), id) { products, ids ->
            val adapter = ProductsAdapter(this, products, ids)
            val listView: ListView = findViewById(R.id.listView)
            listView.adapter = adapter

            listView.setOnItemClickListener { adapterView, view, itemPosition, l ->
                if (type == "gerente") {
                    val activityDeliverProduct = Intent(this, DeliverProductActivity::class.java)
                    activityDeliverProduct.putExtra("id", ids[itemPosition])
                    startActivity(activityDeliverProduct)
                } else {
                    val activityCreateSchedule = Intent(this, CreateScheduleActivity::class.java)
                    activityCreateSchedule.putExtra("id", ids[itemPosition])
                    startActivity(activityCreateSchedule)
                }
            }
        }
    }

    fun getUserInfo(){
        userService.getUser(place.user_email){ user ->
            val user_name: TextView = findViewById(R.id.manager_name)
            user_name.setText(user.name)
        }
    }

    private fun getPlaceInfo(){
        shopService.getPlaceById(id) {place ->
            this.place = place

            getUserInfo()
        }
    }

    private fun onClickAddProductButton (){
        val btnNavigationCreateShop: TextView = findViewById(R.id.add_product_button)
        btnNavigationCreateShop.setOnClickListener{
            val activityCreateProduct = Intent(this, CreateProductActivity::class.java)
            activityCreateProduct.putExtra("id", id)

            startActivity(activityCreateProduct)
        }
    }

    private fun onClickLocalizationNav (){
        val btnNavigationCreateShop: TextView = findViewById(R.id.localization_nav)
        btnNavigationCreateShop.setOnClickListener{
            locationService.getLocation(place.location) { lat, long ->
                val activityMap = Intent(this, MapActivity::class.java)
                activityMap.putExtra("lat", lat)
                activityMap.putExtra("long", long)

                startActivity(activityMap)
            }
        }
    }
}