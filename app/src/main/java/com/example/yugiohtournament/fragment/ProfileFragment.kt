package com.example.yugiohtournament.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.yugiohtournament.R
import com.example.yugiohtournament.database.YgoDatabase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    private val TAG = "ProfileFragment"
    private val ygoDB = YgoDatabase.getInstance().ygoDatabaseDao()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        val currentUser = auth.currentUser
        val userEmail = currentUser?.email.toString()

        val user = ygoDB.getUserByEmail(userEmail)
        if (user == null)
            Log.d(TAG, "user ne postoji")
        else
        {
            username_textView_profile.text = "Username: " + user.username
            team_textView_profile.text = "Team: " + user.team
            konamiID_textView_profile.text = "Konami ID: " + user.konamiID
            email_textView_profile.text = "Email: " + user.email
        }

    }

}