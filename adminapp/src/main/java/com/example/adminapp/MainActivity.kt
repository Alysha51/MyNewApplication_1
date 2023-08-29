package com.example.adminapp

import android.content.Intent
//import android.os.Build.VERSION_CODES.R
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
import com.example.adminapp.R

class MainActivity : AppCompatActivity() {
    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var loginButton: Button? = null
    private var registerButton: Button? = null
    private var firebaseAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
        registerButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                registerUser()
            }

            private fun registerUser() {
                val intent = Intent(applicationContext, RegistrationActivity::class.java)
                startActivity(intent)
            }
        })
        firebaseAuth = FirebaseAuth.getInstance()
        loginButton!!.setOnClickListener(View.OnClickListener { loginUser() })
    }

    private fun loginUser() {
        val email = emailEditText!!.text.toString().trim { it <= ' ' }
        val password = passwordEditText!!.text.toString().trim { it <= ' ' }
        Log.d("Registration", "email = $email")
        Log.d("Registration", "password = $password")
        firebaseAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task: Task<AuthResult?> ->
                    if (task.isSuccessful) {
                        // Login successful, now you can perform any required action
                        val currentUser = firebaseAuth!!.currentUser
                        val userId = currentUser!!.uid
                        if (currentUser != null) {
                            // Check if the entered email is the admin email
                            if ("test@gmail.com" == email) {
                                Toast.makeText(this, "successfully login", Toast.LENGTH_SHORT).show()

                                // User is admin, launch admin activity
                                startActivity(Intent(this, CategoryActivity::class.java))
                                finish() // Optional: Close the sign-in activity
                                //                                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class); startActivity(intent);


                                // Allow admin access
                            } else {
                                // Allow regular user access
                                // User is admin, launch admin activity
                                startActivity(Intent(this, CategorySelectorActivity::class.java))
                                finish() // Optional: Close the sign-in activity
                                //                                Intent intent = new Intent(getApplicationContext(), PageActivity.class); startActivity(intent);
                            }
                        }
                        // Optional: Show a success message or navigate to the next activity
                    } else {
                    }
                }
    }
}