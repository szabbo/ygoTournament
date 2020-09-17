package com.example.yugiohtournament

object SelectedTournament {
    private var selectedID: Int = 0
    private var selectedEmail: String = ""

    fun setSelectedID(newID: Int) {
        this.selectedID = newID
    }

    fun getSelectedID(): Int = selectedID

    fun setSelectedEmail(newEmail: String) {
        this.selectedEmail = newEmail
    }

    fun getSelectedEmail(): String = selectedEmail
}