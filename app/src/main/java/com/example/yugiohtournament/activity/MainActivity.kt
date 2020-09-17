package com.example.yugiohtournament.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.yugiohtournament.R
import com.example.yugiohtournament.fragment.ProfileFragment
import com.example.yugiohtournament.fragment.SearchFragment
import com.example.yugiohtournament.fragment.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val profileFragment = ProfileFragment()
        val searchFragment = SearchFragment()
        val settingsFragment = SettingsFragment()

        setCurrentFragment(profileFragment)

        bottom_nav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_profile -> setCurrentFragment(profileFragment)
                R.id.nav_search -> setCurrentFragment(searchFragment)
                R.id.nav_settings -> setCurrentFragment(settingsFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.wrapper_frame_main_activity, fragment)
        commit()
    }
}