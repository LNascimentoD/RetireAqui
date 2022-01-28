package com.example.retireaqui.views.manager_context

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.retireaqui.R
import com.example.retireaqui.network.services.ShopService
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CreateShopActivity : AppCompatActivity() {
    var auth = Firebase.auth
    val shopService = ShopService()

    lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
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
            shopService.createPlace(place_name.toString(), email,place_lat.toString(), place_long.toString()) { result ->
                if(result){
                    val activityListShop = Intent(this, ListShopActivity::class.java)
                    startActivity(activityListShop)
                }else{
                    Toast.makeText(
                        applicationContext,
                        "Não foi possível persistir os dados do estabelecimento",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}