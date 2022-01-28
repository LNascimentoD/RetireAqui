package com.example.retireaqui.views.manager_context

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.*
import com.example.retireaqui.R
import com.example.retireaqui.network.services.ProductService

class CreateProductActivity : AppCompatActivity() {
    val productService = ProductService()

    lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_create_product)

        id = intent.getStringExtra("id").toString()

        onClickCreate()
    }

    private fun onClickCreate(){
        val editName: EditText = findViewById(R.id.create_product_name_value)
        val editEmail: EditText = findViewById(R.id.create_product_email_value)

        val product_name = editName.getText()
        val email = editEmail.getText()

        val btnOnCreate = findViewById<Button>(R.id.create_product_button)
        btnOnCreate.setOnClickListener {
            productService.createProduct(product_name.toString(), email.toString(), id) { result ->
                if(result){
                    val activityShopDetail = Intent(this, ShopDetailActivity::class.java)
                    activityShopDetail.putExtra("id", id)
                    startActivity(activityShopDetail)
                }else{
                    Toast.makeText(
                        applicationContext,
                        "Não foi possível persistir os dados do produto",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}