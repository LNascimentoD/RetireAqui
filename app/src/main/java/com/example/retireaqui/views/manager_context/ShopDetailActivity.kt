package com.example.retireaqui.views.manager_context

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
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
import com.example.retireaqui.views.user_context.CreateScheduleActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ShopDetailActivity : AppCompatActivity() {
    var auth = Firebase.auth
    var database = Firebase.firestore

    lateinit var user: User
    lateinit var place: Place
    lateinit var id: String
    lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_detail)

        id = intent.getStringExtra("id").toString()

        database.collection("users")
            .whereEqualTo("email", auth.currentUser?.email.toString())
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    type = document.data["type"].toString()

                    val btnNavigationCreateShop: TextView = findViewById(R.id.add_product_button)

                    if(type != "gerente"){
                        btnNavigationCreateShop.isVisible = false
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }

        getPlaceInfo()
        showListView()
        onClickAddProductButton()
        onClickLocalizationNav ()
    }

    private fun showListView(){
        val productList: ArrayList<Product> = ArrayList()
        val productListId: ArrayList<String> = ArrayList()

        database.collection("products")
            .whereEqualTo("place_id", id)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var product = Product(document.data["name"].toString(), document.data["user_email"].toString(), document.data["place_id"].toString())

                    productList.add(product)
                    productListId.add(document.id)
                }

                val adapter = ProductsAdapter(this, productList, productListId)
                val listView: ListView = findViewById(R.id.listView)
                listView.adapter = adapter

                listView.setOnItemClickListener { adapterView, view, itemPosition, l ->
                    Toast.makeText(
                        applicationContext,
                        "111111 $itemPosition",
                        Toast.LENGTH_LONG
                    ).show()

                    if(type == "gerente"){
                        val activityDeliverProduct = Intent(this, DeliverProductActivity::class.java)
                        activityDeliverProduct.putExtra("id", productListId[itemPosition])
                        startActivity(activityDeliverProduct)
                    }else{
                        val activityCreateSchedule = Intent(this, CreateScheduleActivity::class.java)
                        activityCreateSchedule.putExtra("id", productListId[itemPosition])
                        startActivity(activityCreateSchedule)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    private fun getUserInfo(){
        database.collection("users")
            .whereEqualTo("email", place.user_email)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var id = auth.currentUser?.uid.toString()
                    var name = document.data["name"].toString()
                    var email = document.data["email"].toString()
                    var type = document.data["type"].toString()

                    user = User(id, name, email, type)

                    val user_name: TextView = findViewById(R.id.manager_name)
                    user_name.setText(user.name)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    private fun getPlaceInfo(){
        database.collection("places")
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                var name = document.data?.get("name").toString()
                var user_email = document.data?.get("user_email").toString()
                var location = document.data?.get("location").toString()

                place = Place(name, user_email, location)

                getUserInfo()
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
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
            database.collection("location")
                .document(place.location)
                .get()
                .addOnSuccessListener { document ->
                    Toast.makeText(
                        applicationContext,
                        "Deu bom" + document.data?.get("lat").toString() + place.location,
                        Toast.LENGTH_LONG
                    ).show()

                    val activityMap = Intent(this, MapActivity::class.java)
                    activityMap.putExtra("lat", document.data?.get("lat").toString())
                    activityMap.putExtra("long", document.data?.get("long").toString())

                    startActivity(activityMap)
                }
                .addOnFailureListener { exception ->
                    Log.w(ContentValues.TAG, "Error getting documents.", exception)
                }
        }
    }
}