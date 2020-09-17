//package com.example.yugiohtournament.repository
//
//import com.example.yugiohtournament.database.YgoDatabase
//import com.example.yugiohtournament.model.Tournament
//
//object TournamentRepository {
//    val tournaments: MutableList<Tournament>
//    var tournamentID: Int = 0
//
//    private val ygoDB = YgoDatabase.getInstance().ygoDatabaseDao()
//
//    init {
//        tournaments = retrieveTournaments()
//    }
//
//    private fun retrieveTournaments(): MutableList<Tournament> = ygoDB.getAllTournaments().toMutableList()
//
//
//    fun removeTournament(id: Int) = tournaments.removeAll { tournament -> tournament.tournamentIdTournament == id }
//    fun getTournament(id: Int): Tournament? {
//        tournamentID = id
//        return tournaments.find { tournament -> tournament.tournamentIdTournament == id }
//    }
//    fun addTournament(tournament: Tournament) = tournaments.add(tournament)
//    fun getSelectedId(): Int = tournamentID
//}