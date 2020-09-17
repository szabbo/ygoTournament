package com.example.yugiohtournament.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.yugiohtournament.YgoTournamentOrganizer
import com.example.yugiohtournament.model.Tournament
import com.example.yugiohtournament.model.User

@Database(version = 3, entities = [User::class, Tournament::class], exportSchema = false)
abstract class YgoDatabase : RoomDatabase() {
    abstract fun ygoDatabaseDao(): YgoDatabaseDao

    companion object {
        private const val NAME = "YgoDatabase"
        private var INSTANCE: YgoDatabase? = null

        fun getInstance(): YgoDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    YgoTournamentOrganizer.ApplicationContext,
                    YgoDatabase::class.java,
                    NAME
                ).allowMainThreadQueries().build()
            }
            return INSTANCE as YgoDatabase
        }
    }
}