package com.example.retireaqui.views.manager_context

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.retireaqui.R
import com.example.retireaqui.network.models.Place
import com.example.retireaqui.network.models.Product
import com.example.retireaqui.network.models.User
import com.example.retireaqui.network.services.ProductService
import com.example.retireaqui.network.services.ScheduleService
import com.example.retireaqui.network.services.UserService
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DeliverProductActivity : AppCompatActivity() {
    var database = Firebase.firestore
    val productService = ProductService()
    val userService = UserService()
    val scheduleService = ScheduleService()

    lateinit var id_product: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_deliver_product)

        id_product = intent.getStringExtra("id").toString()


        val deliver_info: TextView = findViewById(R.id.deliver_info)
        deliver_info.isVisible = false


        val product_cod: TextView = findViewById(R.id.product_cod)
        product_cod.isVisible = false

        scheduleService.getScheduleByProductId(id_product) { id, schedule ->
            getUser(schedule.user_email)
            getProduct(schedule.product_id)

            val deliver_cod: TextView = findViewById(R.id.deliver_cod)
            deliver_cod.setText(id)

            val time_label: TextView = findViewById(R.id.time_label)
            time_label.setText("O tempo de entrega é: " + schedule.time + schedule.date)
        }

        onClickDeliver()
    }

    private fun getUser(email: String){
        userService.getUser(email){ user ->
            val user_name: TextView = findViewById(R.id.user_name_label)
            user_name.setText("Dono do produto: " + user.name)
        }
    }

    private fun getProduct(id: String){
        productService.getProductById(id) {product ->
            val product_name: TextView = findViewById(R.id.product_name_label)
            product_name.setText("O nome do produto: " + product.name)
        }
    }

    private fun onClickDeliver(){
        val btnOnDate = findViewById<Button>(R.id.button_deliver)

        btnOnDate.setOnClickListener{
            val deliver_info: TextView = findViewById(R.id.deliver_info)
            deliver_info.isVisible = true


            val product_cod: TextView = findViewById(R.id.product_cod)
            product_cod.setText("Código do produto: " + id_product)
            product_cod.isVisible = true
        }
    }
}