package com.example.yugiohtournament.activity.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.yugiohtournament.R
import com.example.yugiohtournament.activity.MainActivity
import com.example.yugiohtournament.database.YgoDatabase
import com.example.yugiohtournament.model.Tournament
import com.example.yugiohtournament.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    val TAG = "RegisterActivity"
    private val auth = FirebaseAuth.getInstance()
    private val ygoDB = YgoDatabase.getInstance().ygoDatabaseDao()
    private val firebaseDB = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_button_register.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val username = username_edittext_register.text.toString()
        val team = team_edittext_register.text.toString()
        val konamiID = konamiID_edittext_register.text.toString()
        val konamiPass = konamiPass_edittext_register.text.toString()
        val email = email_edittext_register.text.toString().toLowerCase()
        val pass = password_edittext_register.text.toString()
        val confirmPass = confirm_edittext_register.text.toString()

        if (email.isNotEmpty() && pass.isNotEmpty() && pass == confirmPass)
        {
            val newUser = User(0, username, team, konamiID, konamiPass, email, pass)

            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
                if (it.isSuccessful)
                {
                    ygoDB.insertUser(newUser)
                    writeNewUserFirebase(newUser)
                    Log.d(TAG, ygoDB.getUserByEmail(email).toString())
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else
                    Toast.makeText(this, "Something went wrong, please check your email and password.", Toast.LENGTH_SHORT).show()
            }
            Log.d(TAG, newUser.toString())
        }
    }

    private fun writeNewUserFirebase(newUser: User) {
        firebaseDB.child("users").child(newUser.userIdUsers.toString()).setValue(newUser)
    }
}