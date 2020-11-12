package com.example.NewTrip.Screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.NewTrip.Adapters.RvAdapter
import com.example.NewTrip.Database.TripEntity
import com.example.NewTrip.R
import com.example.NewTrip.TripClass
import com.example.NewTrip.ViewModel.TripViewModel
import kotlinx.android.synthetic.main.fragment_list_trip.*

class ListTripMain : Fragment(R.layout.fragment_list_trip),RvAdapter.OnItemClickListener {
    private lateinit var tripViewModel: TripViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        floatingActionButton2.setOnClickListener {

            findNavController().navigate(R.id.action_listTrip_to_addTrip)
        }
        tripViewModel = ViewModelProvider(this).get(TripViewModel::class.java)
        tripViewModel.getTrips.observe(viewLifecycleOwner, Observer {

            val adapter = RvAdapter(it, this)
            adapter.notifyDataSetChanged()
            rv.adapter = adapter


            tv_count.text = if (it.size == 1) "${it.size.toString()} Trip" else "${it.size.toString()} Trips"

        })

    }


    override fun showMenu(view: View, trip: TripEntity) {

        val popupMenu = PopupMenu(view.context, view)
        popupMenu.inflate(R.menu.rv_menu)
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    tripViewModel.deleteTrip(trip)
                }
                R.id.update -> {

                  val action = ListTripMainDirections.actionListTripToAddTrip(trip)
                    view.findNavController().navigate(action)
                }
            }
            true
        }
    }

}