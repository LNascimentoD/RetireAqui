package com.example.retireaqui.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.retireaqui.R
import com.example.retireaqui.network.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    var auth = Firebase.auth
    var database = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_register)

        onRegister()
    }

    private fun onRegister() {
        val editName: EditText = findViewById(R.id.login_email_value)
        val editEmail: EditText = findViewById(R.id.register_email_value)
        val editPassword: EditText = findViewById(R.id.login_password_value)
        val editType: CheckBox = findViewById(R.id.register_type_checkbox)

        val name = editName.getText()
        val email = editEmail.getText()
        val password = editPassword.getText()

        val btnOnRegister = findViewById<Button>(R.id.register_button)
        btnOnRegister.setOnClickListener {
            if(editType.isChecked){
                signUp(name.toString(), email.toString(), password.toString(), "gerente")
            }else{
                signUp(name.toString(), email.toString(), password.toString(), "cliente")
            }
        }
    }

    private fun signUp(name: String, email: String, password: String, type: String) {
        auth.createUserWithEmailAndPassword(email, password).
        addOnCompleteListener { task: Task<AuthResult> ->
            if(task.isSuccessful){
                val user = User(auth.currentUser?.uid.toString(), name, email, type)

                database.collection("users")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        val activityMap = Intent(this, MapActivity::class.java)
                        startActivity(activityMap)
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            applicationContext,
                            "Não foi possível persistir os dados do usuário",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }else{
                Toast.makeText(
                    applicationContext,
                    "${task.exception?.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}