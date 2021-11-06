package com.example.retireaqui.network.services

import android.content.ContentValues.TAG
import android.util.Log
import com.example.retireaqui.network.models.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserService {
    val db = Firebase.firestore

    public fun registerUser(user: User){
        val user = hashMapOf(
            "name" to user.name,
            "email" to user.email,
            "password" to user.password,
            "tipo" to "gerente"
        )

        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}