package com.example.googlemaps

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.*

class EmailFragment : Fragment() {

    private lateinit var emailEditText: EditText
    private lateinit var sendEmailButton: Button
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_email, container, false)

        emailEditText = view.findViewById(R.id.emailEditText)
        sendEmailButton = view.findViewById(R.id.sendEmailButton)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val lastEmail = SharedPreferencesHelper.getLastEmail(requireContext())
        emailEditText.setText(lastEmail)

        sendEmailButton.setOnClickListener { sendEmailWithLocation() }

        return view
    }

    private fun sendEmailWithLocation() {
        val emailAddress = emailEditText.text.toString().trim()

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { location ->
            if (location != null) {
                val latitude = location.latitude.toString()
                val longitude = location.longitude.toString()
                val address = getAddressFromLocation(location)

                val subject = "My Current Location"
                val body = "Latitude: $latitude\nLongitude: $longitude\nAddress: $address"

                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.type = "text/plain"
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
                emailIntent.putExtra(Intent.EXTRA_TEXT, body)

                if (emailIntent.resolveActivity(requireActivity().packageManager) != null) {
                    SharedPreferencesHelper.saveLastEmail(requireContext(), emailAddress)
                    startActivity(Intent.createChooser(emailIntent, "Send Email"))
                }
            }
        }
    }

    private fun getAddressFromLocation(location: Location): String {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val address = addresses[0]
                    val addressStringBuilder = StringBuilder()

                    for (i in 0..address.maxAddressLineIndex) {
                        addressStringBuilder.append(address.getAddressLine(i))
                        if (i < address.maxAddressLineIndex) {
                            addressStringBuilder.append(", ")
                        }
                    }

                    return addressStringBuilder.toString()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return "Unknown Address"
    }
}
