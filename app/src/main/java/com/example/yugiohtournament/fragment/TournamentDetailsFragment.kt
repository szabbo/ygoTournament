package com.example.yugiohtournament.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.yugiohtournament.YgoTournamentOrganizer
import com.example.yugiohtournament.R
import com.example.yugiohtournament.SelectedTournament
import com.example.yugiohtournament.activity.GoogleMapsActivity
import com.example.yugiohtournament.database.YgoDatabase
import kotlinx.android.synthetic.main.fragment_tournament_details.*

class TournamentDetailsFragment : Fragment() {

    private val TAG = "TournamentDetailsFragment"
    private val ygoDB = YgoDatabase.getInstance().ygoDatabaseDao()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =  inflater.inflate(R.layout.fragment_tournament_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        map_btn_tournament_details_fragment.setOnClickListener {
            val intent = Intent(YgoTournamentOrganizer.ApplicationContext, GoogleMapsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupUI() {
        val selectedTournament = ygoDB.getTournamentById(SelectedTournament.getSelectedID())
        name_textview_tournament_details_fragment.text = selectedTournament?.name.toString().replaceFirst("name:", "Name:")
        address_textview_tournament_details_fragment.text = selectedTournament?.address.toString().replaceFirst("address:", "Address:")
        city_textview_tournament_details_fragment.text = selectedTournament?.city.toString().replaceFirst("city:", "City:")
        date_textview_tournament_details_fragment.text = selectedTournament?.date.toString().replaceFirst("date:", "Date:")
        time_textview_tournament_details_fragment.text = selectedTournament?.time.toString().replaceFirst("time:", "Time:") + " h"
    }

    companion object {
        fun newInstance(): Fragment {
            return TournamentDetailsFragment()
        }
    }
}