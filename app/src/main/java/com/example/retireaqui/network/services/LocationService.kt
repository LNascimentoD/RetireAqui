package com.example.retireaqui.network.services

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LocationService {
    var auth = Firebase.auth
    var database = Firebase.firestore

    fun getLocation(id: String, setResult: (lat: String, long: String) -> Unit){
        database.collection("location")
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                setResult(document.data?.get("lat").toString(), document.data?.get("long").toString())
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }
}