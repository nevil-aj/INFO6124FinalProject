package com.example.googlemaps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlacesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var placeAdapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_places, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val places = getNearbyPlaces()

        placeAdapter = PlaceAdapter(places)
        recyclerView.adapter = placeAdapter

        return view
    }

    private fun getNearbyPlaces(): List<Place> {
        val places = ArrayList<Place>()
        places.add(Place("Butterbites Café and Restaurant", "1494 Dundas St, London, ON N5W 3B9, Canada"))
        places.add(Place("London International Airport", "10 Seabrook Way, London, ON N5V 3B6, Canada"))
        places.add(Place("Petro-Canada", "2200 Dundas St E, London, ON N5V 1R5, Canada"))
        places.add(Place("Victoria & Children’s Hospital", "800 Commissioners Rd E, London, ON N6A 5W9, Canada"))
        places.add(Place("Downtown London BIA", "123 King St, London, ON N6A 1C3, Canada"))
        return places
    }

    data class Place(val name: String, val address: String)

    inner class PlaceAdapter(private val places: List<Place>) : RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
            return PlaceViewHolder(view)
        }

        override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
            val place = places[position]
            holder.nameTextView.text = place.name
            holder.addressTextView.text = place.address
        }

        override fun getItemCount(): Int {
            return places.size
        }

        inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
            val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)
        }
    }
}
