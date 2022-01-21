package com.example.retireaqui.views.manager_context

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.example.retireaqui.R
import com.example.retireaqui.network.models.Place
import com.example.retireaqui.network.models.Product
import com.example.retireaqui.network.models.User
import com.example.retireaqui.views.adapters.ProductsAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DeliverProductActivity : AppCompatActivity() {
    var database = Firebase.firestore

    lateinit var user: User
    lateinit var place: Place
    lateinit var product: Product

    lateinit var id_product: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deliver_product)

        id_product = intent.getStringExtra("id").toString()

        database.collection("schedule")
            .whereEqualTo("product_id", id_product)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    getUser(document.data["user_email"].toString())
                    getPlace(document.data["place_id"].toString())
                    getProduct(document.data["product_id"].toString())

                    val deliver_cod: TextView = findViewById(R.id.deliver_cod)
                    deliver_cod.setText(document.id)

                    val time_label: TextView = findViewById(R.id.time_label)
                    time_label.setText("O tempo de entrega é: " + document.data["time"].toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    private fun getUser(email: String){
        database.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var id = document.id
                    var name = document.data["name"].toString()
                    var email = document.data["email"].toString()
                    var type = document.data["type"].toString()

                    user = User(id, name, email, type)

                    val user_name: TextView = findViewById(R.id.user_name_label)
                    user_name.setText("Dono do produto: " + user.name)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    private fun getPlace(id: String){}

    private fun getProduct(id: String){
        database.collection("products")
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                var name = document.data?.get("name").toString()
                var user_email = document.data?.get("user_email").toString()
                var place_id = document.data?.get("location").toString()

                product = Product(name, user_email, place_id)

                val product_name: TextView = findViewById(R.id.product_name_label)
                product_name.setText("O nome do produto: " + product.name)
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }
}