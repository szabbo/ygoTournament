package com.example.yugiohtournament.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.yugiohtournament.R
import com.example.yugiohtournament.database.YgoDatabase
import com.example.yugiohtournament.model.Tournament
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_new.*
import java.util.*

class AddNewTournamentActivity : AppCompatActivity() {

    private val ygoDB = YgoDatabase.getInstance().ygoDatabaseDao()
    private val auth = FirebaseAuth.getInstance()
    private val firebaseDB = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new)

        add_button_add_new.setOnClickListener {
            addNew()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addNew() {
        val name = name_editText_add_new.text.toString()
        val address = address_editText_add_new.text.toString()
        val city = city_editText_add_new.text.toString()
        val date = date_editText_add_new.text.toString()
        val time = time_editText_add_new.text.toString()
//        val currentUser = ygoDB.getUserByEmail(auth.currentUser?.email.toString())
        val email = auth.currentUser?.email.toString()
        val newId = ygoDB.getAllTournaments().size
//        val newId = UUID.randomUUID().toString()

        Log.d("SearchFragment", "AddNewTournament " + ygoDB.getAllTournaments().size.toString())

        val id = ygoDB.insertTournament(Tournament(
            newId,
            "email: $email",
            "name: $name",
            "address: $address",
            "city: $city",
            "date: $date",
            "time: $time")) - 1

        val newTournament = Tournament(
            newId,
            "email: $email",
            "name: $name",
            "address: $address",
            "city: $city",
            "date: $date",
            "time: $time")

        Log.d("SearchFragment", "newTournament: " + ygoDB.getTournamentByEmail(email))
        Log.d("SearchFragment", "DOHVACENI ID: " + id)
        Log.d("SearchFragment", "NEW ID: " + newId)
        firebaseDB.child("tournaments").child(id.toString()).setValue(newTournament)
//        writeNewTournamentFirebase(newTournament)
    }

    private fun writeNewTournamentFirebase(newTournament: Tournament) {
        firebaseDB.child("tournaments").child(newTournament.tournamentIdTournament.toString()).setValue(newTournament)
    }
}