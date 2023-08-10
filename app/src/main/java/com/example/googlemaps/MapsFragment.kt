package com.example.googlemaps

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.googlemaps.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.*

class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var binding: FragmentMapsBinding
    private var yourMarker: MarkerOptions? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val yourLn = LatLng(43.013309, -81.199339)

        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(yourLn.latitude, yourLn.longitude, 1)
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val address: Address = addresses[0]
                    val addressString = address.getAddressLine(0)
                    val city = address.locality
                    val state = address.adminArea
                    val country = address.countryName
                    val title = "Your Location: $addressString, $city, $state, $country"

                    map.addMarker(
                        MarkerOptions()
                            .position(yourLn)
                            .title(title)
                            .snippet("Latitude: ${yourLn.latitude}, Longitude: ${yourLn.longitude}")
                    )
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLn, 15f))
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        marker.showInfoWindow()
        return true
    }
}
