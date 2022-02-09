package com.example.retireaqui.network.services

import android.content.ContentValues
import android.util.Log
import com.example.retireaqui.network.models.Location
import com.example.retireaqui.network.models.Place
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ShopService {
    var auth = Firebase.auth
    var database = Firebase.firestore

    fun createPlace(name: String, email: String, lat: String, long: String, setResult: (result: Boolean) -> Unit){
        createLocation(name, lat, long) { idLocation ->
            val place = Place(name, email, idLocation)

            database.collection("places")
                .add(place)
                .addOnSuccessListener { documentReference ->
                    setResult(true)
                }
                .addOnFailureListener { e ->
                    setResult(false)
                }
        }
    }

    fun createLocation(name: String, lat: String, long: String, setIdLocation: (result: String) -> Unit){
        val location = Location(name, lat, long)

        database.collection("location")
            .add(location)
            .addOnSuccessListener { documentReference ->
                setIdLocation(documentReference.id)
            }
            .addOnFailureListener { e ->
            }
    }

    fun getAllPlace(setResult: (places: ArrayList<Place>, ids: ArrayList<String>) -> Unit){
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

                setResult(listPlaces, listPlacesId)
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    fun getPlaceByEmail(email: String, setResult: (places: ArrayList<Place>, ids: ArrayList<String>) -> Unit){
        val listPlaces : ArrayList<Place> = ArrayList()
        val listPlacesId : ArrayList<String> = ArrayList()

        database.collection("places")
            .whereEqualTo("user_email", email)
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

                setResult(listPlaces, listPlacesId)
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    fun getPlaceById(id: String, setPlace: (place: Place) -> Unit){
        database.collection("places")
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                var name = document.data?.get("name").toString()
                var user_email = document.data?.get("user_email").toString()
                var location = document.data?.get("location").toString()

                var place = Place(name, user_email, location)

                setPlace(place)
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }
}