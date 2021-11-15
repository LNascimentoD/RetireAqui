package com.example.retireaqui.network.models

import android.content.ContentValues.TAG
import android.util.Log
import com.example.retireaqui.network.providers.HashProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object Authentication {
    lateinit var user: User
    val db = Firebase.firestore

    fun login(email: String, password: String): Boolean{
        val hashProvider = HashProvider()

        db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if(hashProvider.compare(password, document.data.get("password").toString())){
                        user = User(document.data.get("name").toString(), document.data.get("email").toString(), document.data.get("password").toString())
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        if(user.name != ""){
            return true
        }

        return false
    }

    fun getCurrentUser() {
        println(user.name + " " + user.email + " " + user.password)
    }

    fun logout() {}
}