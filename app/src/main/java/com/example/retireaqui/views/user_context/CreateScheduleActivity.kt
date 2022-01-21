package com.example.retireaqui.views.user_context

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.retireaqui.R
import com.example.retireaqui.network.models.Place
import com.example.retireaqui.network.models.Product
import com.example.retireaqui.network.models.Schedule
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class CreateScheduleActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    var database = Firebase.firestore
    var auth = Firebase.auth

    lateinit var id: String

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_schedule)

        id = intent.getStringExtra("id").toString()

        onClickCreate()
        pickDate()
        pickTime()
    }

    private fun getDateTimeCalendar(){
        val cal: Calendar = Calendar.getInstance()

        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun pickDate(){
        val btnOnDate = findViewById<Button>(R.id.create_schedule_date_value)

        btnOnDate.setOnClickListener{
            getDateTimeCalendar()

            DatePickerDialog(this, this, year, month, day).show()
        }
    }

    private fun pickTime(){
        val btnOnTime = findViewById<Button>(R.id.create_schedule_time_value)

        btnOnTime.setOnClickListener{
            getDateTimeCalendar()
            TimePickerDialog(this, this, hour, minute, true).show()
        }
    }

    private fun onClickCreate(){
        val btnOnCreate = findViewById<Button>(R.id.create_schedule_button)
        btnOnCreate.setOnClickListener {
            createSchedule(auth.currentUser?.email.toString(), id)
        }
    }

    private fun createSchedule(user_email: String, product_id: String){
        val schedule = Schedule("$savedDay/$savedMonth/$savedYear", "$savedHour:$savedMinute", user_email, product_id)

        database.collection("schedule")
            .add(schedule)
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

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        savedDay = p3
        savedMonth = p2 + 1
        savedYear = p1

        val btnOnDate = findViewById<Button>(R.id.create_schedule_date_value)
        btnOnDate.setText("$savedDay/$savedMonth/$savedYear")
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        savedHour = p1
        savedMinute = p2

        val btnOnTime = findViewById<Button>(R.id.create_schedule_time_value)
        btnOnTime.setText("$savedHour:$savedMinute")
    }
}