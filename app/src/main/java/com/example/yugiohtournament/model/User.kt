package com.example.yugiohtournament.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = true)var userIdUsers: Int,
    @ColumnInfo(name = "username")var username: String,
    @ColumnInfo(name = "team")var team: String,
    @ColumnInfo(name = "konamiID")var konamiID: String,
    @ColumnInfo(name = "konamiPass")var konamiPass: String,
    @ColumnInfo(name = "email")var email: String,
    @ColumnInfo(name = "pass")var pass: String
)