package com.example.retireaqui.network.services

import com.example.retireaqui.network.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AuthService {
    var auth = Firebase.auth
    var database = Firebase.firestore

    fun signIn(email: String, password: String, setResult: (result: Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).
        addOnCompleteListener { task ->
            var result = task.isSuccessful

            setResult(result)
        }
    }

    fun signUp(name: String, email: String, password: String, type: String, setResult: (result: Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).
        addOnCompleteListener { task: Task<AuthResult> ->
            if(task.isSuccessful){
                val user = User(auth.currentUser?.uid.toString(), name, email, type)

                database.collection("users")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        var result = true

                        setResult(result)
                    }
                    .addOnFailureListener { e ->
                        var result = false

                        setResult(result)
                    }
            }else{
                var result = false

                setResult(result)
            }
        }
    }

    fun logout(){
        auth.signOut()
    }
}