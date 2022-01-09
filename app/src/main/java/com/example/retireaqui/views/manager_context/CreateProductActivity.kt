package com.example.retireaqui.views.manager_context

import android.content.ContentValues
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.retireaqui.R
import com.example.retireaqui.network.models.Location
import com.example.retireaqui.network.models.Place
import com.example.retireaqui.network.models.Product
import com.example.retireaqui.network.models.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateProductActivity : AppCompatActivity() {
    var database = Firebase.firestore
    val listPlaces : ArrayList<Place> = ArrayList()

    lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_product)

        id = intent.getStringExtra("id").toString()

        onClickCreate()
    }

    private fun printPlace(){
        val listPlacesName : ArrayList<String> = ArrayList()

        for(place in listPlaces){
            listPlacesName.add(place.name)
        }

        for(name in listPlacesName.toArray()){
            Log.d(TAG, "11111 $name")
        }
    }

    /*
    private fun configureDropDownMenu() {
        val listPlacesName : ArrayList<String> = ArrayList()

        for(place in listPlaces){
            listPlacesName.add(place.name)
        }

        val autocompleteDropDownMenu: AutoCompleteTextView = findViewById(R.id.autoCompleteTextView_dropdownMenu)
        val teams: Array<String> = listPlacesName.toTypedArray()
        val dropdownAdapter: ArrayAdapter<*> = ArrayAdapter(this, R.layout.dropdown_menu_item, teams)
        autocompleteDropDownMenu.setAdapter(dropdownAdapter)

        autocompleteDropDownMenu.setOnItemClickListener { _, _, pos, _ ->
            showToastMessage("Selecionou " + teams[pos] + " no dropdow menu")
        }
    }
    */

    /*
    private fun configureSpinner() {
        val listPlacesName : ArrayList<String> = ArrayList()

        for(place in listPlaces){
            listPlacesName.add(place.name)
        }

        val spinner: Spinner = findViewById(R.id.spinner)
        val teams: Array<String> = listPlacesName.toTypedArray()
        val spinnerAdapter: ArrayAdapter<*> = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, teams)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, l: Long) {
                showToastMessage("Selecionou "+teams[position]+ " na spinner list")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }
*/

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun getPlaces(){
        database.collection("places")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var location = Location("", "")


                    val docRef = database.collection("location").document(document.data["location"].toString())
                    docRef.get()
                        .addOnSuccessListener { document_b ->
                            if (document_b != null) {
                                location.lat = document_b.data?.get("lat").toString()
                                location.long = document_b.data?.get("long").toString()
                            } else {
                                Log.d(TAG, "No such document")
                            }

                            printPlace()
                            //configureDropDownMenu()
                            //configureSpinner()
                        }
                        .addOnFailureListener { exception ->
                            Log.d(TAG, "get failed with ", exception)
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    private fun onClickCreate(){
        val editName: EditText = findViewById(R.id.create_product_name_value)
        val editEmail: EditText = findViewById(R.id.create_product_email_value)

        val product_name = editName.getText()
        val email = editEmail.getText()

        val btnOnCreate = findViewById<Button>(R.id.create_product_button)
        btnOnCreate.setOnClickListener {
            createProduct(product_name.toString(), email.toString(), id)
        }
    }

    private fun createProduct(name: String, user_email: String, location: String){
        val product = Product(name, user_email, location)

        database.collection("products")
            .add(product)
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