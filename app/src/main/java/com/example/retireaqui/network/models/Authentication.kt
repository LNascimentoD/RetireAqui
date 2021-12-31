package com.example.retireaqui.network.models

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object Authentication {
    var auth = Firebase.auth
    var database = Firebase.firestore


    fun signUp(name: String, email: String, password: String, type: String) {
        auth.createUserWithEmailAndPassword(email, password).
        addOnCompleteListener { task: Task<AuthResult> ->
            if(task.isSuccessful){
                val user = User(auth.currentUser?.uid.toString(), name, email, type)

                database.collection("users")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w(ContentValues.TAG, "Error adding document", e)
                    }
            }
        }
    }

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).
        addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information val user = auth.currentUser
                val user = auth.currentUser
                Log.d(TAG, "signInWithEmail:success" + user?.sendEmailVerification())
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.exception)
            }
        }
    }

    fun getCurrentUser() {
        val user = auth.currentUser
        Log.d(TAG, "signInWithEmail:success" + user?.sendEmailVerification())
    }

    fun logout() {
        Firebase.auth.signOut()
    }
}