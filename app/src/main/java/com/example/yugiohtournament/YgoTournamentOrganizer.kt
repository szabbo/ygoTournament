package com.example.yugiohtournament

import android.app.Application
import android.content.Context

class YgoTournamentOrganizer : Application() {
    companion object {
        lateinit var ApplicationContext: Context
            private set
    }
    override fun onCreate() {
        super.onCreate()
        ApplicationContext = this
    }
}