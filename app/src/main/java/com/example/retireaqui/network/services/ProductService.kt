package com.example.retireaqui.network.services

import android.content.ContentValues
import android.util.Log
import com.example.retireaqui.network.models.Product
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductService {
    var auth = Firebase.auth
    var database = Firebase.firestore

    fun createProduct(name: String, user_email: String, location: String, setResult: (result: Boolean) -> Unit){
        val product = Product(name, user_email, location)

        database.collection("products")
            .add(product)
            .addOnSuccessListener { documentReference ->
                setResult(true)
            }
            .addOnFailureListener { e ->
                setResult(false)
            }
    }

    fun getProductByPlaceId(id: String, setResult: (products: ArrayList<Product>, ids: ArrayList<String>) -> Unit){
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

                setResult(productList, productListId)
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    fun getProductByPlaceIdAndUserEmail(user_email: String, place_id: String, setResult: (products: ArrayList<Product>, ids: ArrayList<String>) -> Unit){
        val productList: ArrayList<Product> = ArrayList()
        val productListId: ArrayList<String> = ArrayList()

        database.collection("products")
            .whereEqualTo("place_id", place_id)
            .whereEqualTo("user_email", user_email)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var product = Product(document.data["name"].toString(), document.data["user_email"].toString(), document.data["place_id"].toString())

                    productList.add(product)
                    productListId.add(document.id)
                }

                setResult(productList, productListId)
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    fun getProductById(id: String, setResult: (product: Product) -> Unit){
        database.collection("products")
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                var name = document.data?.get("name").toString()
                var user_email = document.data?.get("user_email").toString()
                var place_id = document.data?.get("location").toString()

                val product = Product(name, user_email, place_id)

                setResult(product)
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    fun deleteProductById(id: String, setResult: (result: Boolean) -> Unit){
        database.collection("products").document(id)
            .delete()
            .addOnSuccessListener { setResult(true) }
            .addOnFailureListener { setResult(false) }
    }
}