package com.example.yugiohtournament.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yugiohtournament.R
import com.example.yugiohtournament.SelectedTournament
import com.example.yugiohtournament.listeners.TournamentInteractionListener
import com.example.yugiohtournament.model.Tournament
import kotlinx.android.synthetic.main.list_item.view.*

// rv, noviMenu i search fragment
class RecycleViewAdapter(tournaments: MutableList<Tournament>, tournamentListener: TournamentInteractionListener) : RecyclerView.Adapter<RecycleViewHolder>() {
    private val tournamentsList: MutableList<Tournament> = mutableListOf()
    private val tournamentListener: TournamentInteractionListener

    init {
        this.tournamentsList.addAll(tournaments)
        this.tournamentListener = tournamentListener
    }

    fun refreshData(tournaments: MutableList<Tournament>) {
        this.tournamentsList.clear()
        this.tournamentsList.addAll(tournaments)
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = tournamentsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        val tournamentView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return RecycleViewHolder(tournamentView)
    }

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        val tournament = tournamentsList[position]
        holder.bindItems(tournament, tournamentListener)
    }
}
class RecycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindItems(tournament: Tournament, tournamentListener: TournamentInteractionListener) {
        itemView.image_imageview_rv_item.setImageResource(R.drawable.ic_millenium_puzzle)
        itemView.title_textview_rv_item.text = tournament.name.replaceFirst("name:", "Name:")
        itemView.description_textview_rv_item.text = tournament.address.replaceFirst("address:", "Address:")

        itemView.setOnClickListener { tournamentListener.onShowDetails(tournament.tournamentIdTournament) }
        itemView.setOnLongClickListener {
            SelectedTournament.setSelectedEmail(tournament.userEmail)
            tournamentListener.onRemove(tournament.tournamentIdTournament)
            true }
    }
}