package com.example.retireaqui.views.manager_context

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.retireaqui.R
import com.example.retireaqui.network.models.Location
import com.example.retireaqui.network.models.Place
import com.example.retireaqui.network.models.Product
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateShopActivity : AppCompatActivity() {
    var auth = Firebase.auth
    var database = Firebase.firestore

    lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_shop)
        email = auth.currentUser?.email.toString()
        onClickCreate()
    }

    private fun onClickCreate(){
        val editName: EditText = findViewById(R.id.create_place_name_value)
        val editLat: EditText = findViewById(R.id.create_place_lat_value)
        val editLong: EditText = findViewById(R.id.create_place_long_value)

        val place_name = editName.getText()
        val place_lat = editLat.getText()
        val place_long = editLong.getText()

        val btnOnCreate = findViewById<Button>(R.id.create_place_button)
        btnOnCreate.setOnClickListener {
            createLocation(place_name.toString(), place_lat.toString(), place_long.toString())
        }
    }

    private fun createLocation(name: String, lat: String, long: String){
        val location = Location(lat, long)

        database.collection("location")
            .add(location)
            .addOnSuccessListener { documentReference ->
                createPlace(name,  documentReference.id)

                Toast.makeText(
                    applicationContext,
                    "Deu bom",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    applicationContext,
                    "Não foi possível persistir os dados do produto",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun createPlace(name: String, location: String){
        val place = Place(name, email, location)

        database.collection("places")
            .add(place)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(
                    applicationContext,
                    "Deu bom",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    applicationContext,
                    "Não foi possível persistir os dados do produto",
                    Toast.LENGTH_LONG
                ).show()
            }
    }
}