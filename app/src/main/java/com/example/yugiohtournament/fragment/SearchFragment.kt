package com.example.yugiohtournament.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yugiohtournament.*
import com.example.yugiohtournament.activity.AddNewTournamentActivity
import com.example.yugiohtournament.adapter.RecycleViewAdapter
import com.example.yugiohtournament.database.YgoDatabase
import com.example.yugiohtournament.extensions.showFragment
import com.example.yugiohtournament.listeners.TournamentInteractionListener
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private val TAG = "SearchFragment"
    private val ygoDB = YgoDatabase.getInstance().ygoDatabaseDao()
    private val firebaseDB = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()

        addBtn_floatingButton_main.setOnClickListener {
            val intent = Intent(activity, AddNewTournamentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupUI() {

        tournamentDisplay.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        tournamentDisplay.itemAnimator = DefaultItemAnimator()
        tournamentDisplay.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        displayData()
    }

    private fun displayData() {
        val tournamentListener = object : TournamentInteractionListener {
            override fun onRemove(id: Int) {
                Log.d(TAG, "ON REMOVE " + id.toString())
                // TournamentRepository.removeTournament(id)
                val removeTournament = ygoDB.getTournamentByEmail(ygoDB.getTournamentById(id).userEmail)
                Log.d(TAG, "dohvaceniEmail: " + removeTournament.toString())

                // nađi turnir

                ygoDB.deleteTournament(ygoDB.getTournamentById(id))
                firebaseDB.child("tournaments").child(removeTournament.tournamentIdTournament.toString()).removeValue()

                //////////// OBRIŠI IZ BAZE
                Log.d(TAG, id.toString())
                (tournamentDisplay.adapter as RecycleViewAdapter).refreshData(
                    ygoDB.getAllTournaments().toMutableList() // tu ide iz baze da dohvati sve i pošalje
                )
            }

            override fun onShowDetails(id: Int) {
                SelectedTournament.setSelectedID(id)
                activity?.showFragment(R.id.wrapper_frame_main_activity, TournamentDetailsFragment.newInstance())
            }

        }
        /////////////////////////////////////////////// Dohvati iz baze ///////////////////////////////////////////////

        tournamentDisplay.adapter =
            RecycleViewAdapter(ygoDB.getAllTournaments().toMutableList(), tournamentListener) // tu ide iz baze da dohvati
    }

    companion object {
        fun newInstance(): Fragment {
            return SearchFragment()
        }
    }
}