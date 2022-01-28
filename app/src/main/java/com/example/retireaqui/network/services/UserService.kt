package com.example.retireaqui.network.services

import android.content.ContentValues
import android.util.Log
import com.example.retireaqui.network.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserService {
    var auth = Firebase.auth
    var database = Firebase.firestore

    fun getUser(email: String, setUser: (user: User) -> Unit){
        database.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var id = document.id
                    var name = document.data["name"].toString()
                    var email = document.data["email"].toString()
                    var type = document.data["type"].toString()

                    var user = User(id, name, email, type)

                    setUser(user)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }
}