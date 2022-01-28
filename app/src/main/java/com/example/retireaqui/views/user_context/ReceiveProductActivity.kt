package com.example.retireaqui.views.user_context

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.retireaqui.R
import com.example.retireaqui.network.services.ProductService
import com.example.retireaqui.network.services.ScheduleService
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ReceiveProductActivity : AppCompatActivity() {
    var auth = Firebase.auth
    val scheduleService = ScheduleService()
    val productService = ProductService()

    lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_receive_product)

        id = intent.getStringExtra("id").toString()

        onClickReceive()
    }

    private fun onClickReceive(){
        val btnOnDate = findViewById<Button>(R.id.receive_button)

        btnOnDate.setOnClickListener{
            scheduleService.getScheduleById(id) { schedule ->
                productService.deleteProductById(schedule.product_id) { result ->
                    if(result){
                        scheduleService.deleteScheduleById(id) { result ->
                            if (result){

                            }
                        }
                    }
                }
            }
        }
    }
}