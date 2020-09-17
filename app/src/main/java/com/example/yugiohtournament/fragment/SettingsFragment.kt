package com.example.yugiohtournament.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.yugiohtournament.YgoTournamentOrganizer
import com.example.yugiohtournament.R
import com.example.yugiohtournament.activity.authentication.LoginActivity
import com.example.yugiohtournament.database.YgoDatabase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    private val TAG = "SettingsFragment"
    private val ygoDB = YgoDatabase.getInstance().ygoDatabaseDao()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        pass_button_settings.setOnClickListener {
            changePass()
        }

        userInfo_button_settings.setOnClickListener {
            updateUserData()
        }

        signout_button_settings.setOnClickListener {
            auth.signOut()
            val intent = Intent(YgoTournamentOrganizer.ApplicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun changePass() {
        val newPass = new_pass_settings.text.toString()
        val confirmPass = confirm_pass_settings.text.toString()

        if (newPass.isNotEmpty() && confirmPass.isNotEmpty()) {
            val user = auth.currentUser
            if (user != null){
                val credential = EmailAuthProvider
                    .getCredential(user.email.toString(), newPass)

                user.reauthenticate(credential).addOnCompleteListener {
                    if (it.isSuccessful)
                    {
                        user.updatePassword(confirmPass)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(YgoTournamentOrganizer.ApplicationContext, "Password changed successfully!", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }

                     else Toast.makeText(YgoTournamentOrganizer.ApplicationContext, "Something went wrong, please try again later!", Toast.LENGTH_SHORT).show()
                }
            }

        } else {
            Toast.makeText(YgoTournamentOrganizer.ApplicationContext, "Email and password successfully updated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUserData() {
        val newUsername = username_settings.text.toString()
        val newTeam = team_settings.text.toString()
        val newKonamiID = konamiID_settings.text.toString()

        val currentUserEmail = auth.currentUser?.email.toString()
        val currentUser = ygoDB.getUserByEmail(currentUserEmail)
        val currentUserID = currentUser.userIdUsers

        if (newUsername.isNotEmpty()) {
            ygoDB.updateUserUsername(newUsername, currentUserID)
            Toast.makeText(YgoTournamentOrganizer.ApplicationContext, "Your username is updated successfully!", Toast.LENGTH_SHORT).show()
        }
        if (newTeam.isNotEmpty()) {
            ygoDB.updateUserTeam(newTeam, currentUserID)
            Toast.makeText(YgoTournamentOrganizer.ApplicationContext, "Your team is updated successfully!", Toast.LENGTH_SHORT).show()
        }
        if(newKonamiID.isNotEmpty()) {
            ygoDB.updateUserKonamiID(newKonamiID, currentUserID)
            Toast.makeText(YgoTournamentOrganizer.ApplicationContext, "Your Konami ID is updated successfully!", Toast.LENGTH_SHORT).show()
        }
        if (newUsername.isNullOrEmpty() && newTeam.isNotEmpty() && newKonamiID.isNotEmpty()) {
            ygoDB.updateUserUsername(newUsername, currentUserID)
            ygoDB.updateUserTeam(newTeam, currentUserID)
            ygoDB.updateUserKonamiID(newKonamiID, currentUserID)
            Toast.makeText(YgoTournamentOrganizer.ApplicationContext, "Your data is updated successfully!", Toast.LENGTH_SHORT).show()
        }
    }
}