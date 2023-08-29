package com.example.adminapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {
    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var registerButton: Button? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var databaseReference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        emailEditText = findViewById(R.id.regEmailEditText)
        passwordEditText = findViewById(R.id.regPasswordEditText)
        registerButton = findViewById(R.id.registerButton)
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        registerButton!!.setOnClickListener(View.OnClickListener { registerUser() })
    }

    private fun registerUser() {
        val email = emailEditText!!.text.toString().trim { it <= ' ' }
        val password = passwordEditText!!.text.toString().trim { it <= ' ' }
        Log.d("Registration", "email = $email")
        Log.d("Registration", "password = $password")
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        firebaseAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task: Task<AuthResult?> ->
                    if (task.isSuccessful) {
                        // Registration successful, now save additional user data to the database
                        val currentUser = firebaseAuth!!.currentUser
                        val userId = currentUser!!.uid
                        Toast.makeText(this, "user has finally registered", Toast.LENGTH_SHORT).show()
                        // Save user data to the database
//                        User user = new User(userId, email); // Assuming you have a User data model
//                        databaseReference.child("users").child(userId).setValue(user);


                        // Optional: Show a success message or navigate to the next activity
                    } else {
                        Log.d("Registration", "error occur = " + task.exception)
                        // Registration failed, handle the error
                        val exception = task.exception as FirebaseAuthException?
                        val errorCode = exception!!.errorCode
                        // Handle the error based on the error code
                        Toast.makeText(this, "errer occur", Toast.LENGTH_SHORT).show()
                    }
                }
    }
}