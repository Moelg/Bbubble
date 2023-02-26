package com.example.bbubble

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var edtUserName : EditText
    private lateinit var edtEmail : EditText
    private lateinit var edtPassword : EditText
    private lateinit var btnLogin : Button
    private lateinit var btnSignup : Button

    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtUserName = findViewById(R.id.user_name)
        edtEmail = findViewById(R.id.email)
        edtPassword = findViewById(R.id.password)
        btnLogin = findViewById(R.id.login_btn)
        btnSignup = findViewById(R.id.signup_btn)

        btnLogin.setOnClickListener {
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
        }

        btnSignup.setOnClickListener {

            val userName = edtUserName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            signUp(userName,email,password)
        }

    }
    private fun signUp(userName: String, email: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    addUserToDatabase(userName, email, mAuth.currentUser?.uid!!)

                    val intent = Intent(this@SignUp, LogIn::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SignUp,"Error occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(userName: String, email: String, uid: String){
        mDbRef = FirebaseDatabase.getInstance("https://bbubble-chat-default-rtdb.europe-west1.firebasedatabase.app").getReference()
        mDbRef.child("user").child(uid).setValue(User(userName,email,uid))
    }
}