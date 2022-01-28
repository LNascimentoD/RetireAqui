package com.example.retireaqui.network.services

import android.content.ContentValues
import android.util.Log
import com.example.retireaqui.network.models.Schedule
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ScheduleService {
    var auth = Firebase.auth
    var database = Firebase.firestore

    fun createSchedule(schedule: Schedule, setResult: (result: Boolean) -> Unit){
        database.collection("schedule")
            .add(schedule)
            .addOnSuccessListener { documentReference ->
                setResult(true)
            }
            .addOnFailureListener { e ->
                setResult(false)
            }
    }

    fun getScheduleByProductId(id: String, setResult: (id: String, schedule: Schedule) -> Unit){
        database.collection("schedule")
            .whereEqualTo("product_id", id)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val time = document.data["time"].toString()
                    val date = document.data["date"].toString()
                    val user_Email = document.data["user_email"].toString()
                    val product_Id = document.data["product_id"].toString()

                    val schedule = Schedule(time, date, user_Email, product_Id)
                    setResult(document.id, schedule)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    fun getAllSchedule(email: String, setResult: (schedules: ArrayList<Schedule>, ids: ArrayList<String>) -> Unit){
        val listSchedule : ArrayList<Schedule> = ArrayList()
        val listScheduleId : ArrayList<String> = ArrayList()

        database.collection("schedule")
            .whereEqualTo("user_email", email)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var time = document.data["time"].toString()
                    var date = document.data["date"].toString()
                    var user_email = document.data["user_email"].toString()
                    var product_id = document.data["product_id"].toString()

                    val schedule = Schedule(time, date, user_email, product_id)

                    listSchedule.add(schedule)
                    listScheduleId.add(document.id)
                }

                setResult(listSchedule, listScheduleId)
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    fun getScheduleById(id: String, setResult: (schedule: Schedule) -> Unit){
        database.collection("schedule")
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                var time = document.data?.get("time").toString()
                var date = document.data?.get("date").toString()
                var user_email = document.data?.get("user_email").toString()
                var product_id = document.data?.get("product_id").toString()

                val schedule = Schedule(time, date, user_email, product_id)

                setResult(schedule)
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    fun deleteScheduleById(id: String, setResult: (result: Boolean) -> Unit){
        database.collection("schedule").document(id)
            .delete()
            .addOnSuccessListener { setResult(true) }
            .addOnFailureListener { setResult(false) }
    }
}