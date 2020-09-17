package com.example.yugiohtournament.database

import androidx.room.*
import com.example.yugiohtournament.model.Tournament
import com.example.yugiohtournament.model.User

@Dao
interface YgoDatabaseDao {
    @Insert
    fun insertUser(user: User)
    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>
    @Query("SELECT * FROM users WHERE userIdUsers = :id")
    fun getUserById(id: Int): User
    @Query("SELECT * FROM users WHERE email LIKE :userEmail")
    fun getUserByEmail(userEmail: String): User
    @Query("UPDATE users SET username = :newUsername WHERE userIdUsers = :newID")
    fun updateUserUsername(newUsername: String, newID: Int)
    @Query("UPDATE users SET team = :newTeam WHERE userIdUsers = :newID")
    fun updateUserTeam(newTeam: String, newID: Int)
    @Query("UPDATE users SET konamiID = :konamiID WHERE userIdUsers = :newID")
    fun updateUserKonamiID(konamiID: String, newID: Int)

    ///////////////////////////////////// BORDER /////////////////////////////////////

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTournament(tournament: Tournament): Long
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun getId(tournament: Tournament): Long
    @Delete
    fun deleteTournament(tournament: Tournament)


    @Query("SELECT * FROM tournaments")
    fun getAllTournaments(): List<Tournament>
    @Query("SELECT * FROM tournaments WHERE tournamentIdTournament = :id")
    fun getTournamentById(id: Int): Tournament
    @Query("SELECT * FROM tournaments WHERE name = :name")
    fun getTournamentByName(name: String): Tournament
    @Query("SELECT * FROM tournaments WHERE email = :email")
    fun getTournamentByEmail(email: String): Tournament
    @Query("DELETE FROM tournaments")
    fun deleteTableTournaments()


}