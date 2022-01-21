package com.example.retireaqui.views.manager_context

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.retireaqui.R
import com.example.retireaqui.network.models.Place
import com.example.retireaqui.network.models.Product
import com.example.retireaqui.network.models.User
import com.example.retireaqui.views.MapActivity
import com.example.retireaqui.views.RegisterActivity
import com.example.retireaqui.views.adapters.ProductsAdapter
import com.example.retireaqui.views.adapters.ShopAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListShopActivity : AppCompatActivity() {
    var auth = Firebase.auth
    var database = Firebase.firestore

    lateinit var user: User
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
                val btnNavigationCreateShop: TextView = findViewById(R.id.add_place_button)

                if(user.type == "gerente"){
                    getPlacesManager()
                }else{
                    getPlacesUser()
                    btnNavigationCreateShop.isVisible = false
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    private fun getPlacesManager(){
        val listPlaces : ArrayList<Place> = ArrayList()
        val listPlacesId : ArrayList<String> = ArrayList()

        database.collection("places")
            .whereEqualTo("user_email", auth.currentUser?.email)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var name = document.data["name"].toString()
                    var user_email = document.data["user_email"].toString()
                    var location = document.data["location"].toString()

                    val place = Place(name, user_email, location)

                    listPlaces.add(place)
                    listPlacesId.add(document.id)
                }

                val adapter = ShopAdapter(this, listPlaces, listPlacesId)
                val listView: ListView = findViewById(R.id.listView_places)
                listView.adapter = adapter

                listView.setOnItemClickListener { adapterView, view, itemPosition, l ->
                    val activityShopDetail = Intent(this, ShopDetailActivity::class.java)
                    activityShopDetail.putExtra("id", listPlacesId[itemPosition])
                    startActivity(activityShopDetail)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    private fun getPlacesUser(){
        val listPlaces : ArrayList<Place> = ArrayList()
        val listPlacesId : ArrayList<String> = ArrayList()

        database.collection("places")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var name = document.data["name"].toString()
                    var user_email = document.data["user_email"].toString()
                    var location = document.data["location"].toString()

                    val place = Place(name, user_email, location)

                    listPlaces.add(place)
                    listPlacesId.add(document.id)
                }

                val adapter = ShopAdapter(this, listPlaces, listPlacesId)
                val listView: ListView = findViewById(R.id.listView_places)
                listView.adapter = adapter

                listView.setOnItemClickListener { adapterView, view, itemPosition, l ->
                    val activityShopDetail = Intent(this, ShopDetailActivity::class.java)
                    activityShopDetail.putExtra("id", listPlacesId[itemPosition])
                    startActivity(activityShopDetail)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    private fun onClickAddProductButton (){
        val btnNavigationCreateShop: TextView = findViewById(R.id.add_place_button)
        btnNavigationCreateShop.setOnClickListener{
            val activityCreateShop = Intent(this, CreateShopActivity::class.java)
            activityCreateShop.putExtra("user_email", auth.currentUser?.email)

            startActivity(activityCreateShop)
        }

        /*
        val btnNavigationRegister: TextView = findViewById(R.id.add_product_button)
        btnNavigationRegister.setOnClickListener{
            database.collection("places")
                .whereEqualTo("user_email", auth.currentUser?.email)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val activityCreateProduct = Intent(this, CreateProductActivity::class.java)
                        activityCreateProduct.putExtra("id", id)

                        startActivity(activityCreateProduct)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(ContentValues.TAG, "Error getting documents.", exception)
                }
        }*/
    }
}