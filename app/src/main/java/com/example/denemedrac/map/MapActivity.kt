package com.example.denemedrac.map

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.denemedrac.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity() {

    private lateinit var viewModel: MapViewModel
    private lateinit var map: GoogleMap
    private lateinit var searchEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)

        searchEditText = findViewById(R.id.search_edit_text)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            map = googleMap
            setupMap()
        }

        // Klavye üzerindeki arama tuşu ile arama işlemini tetikle
        searchEditText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // Arama işlemi
                viewModel.searchPlaces(searchEditText.text.toString())
                return@OnEditorActionListener true
            }
            false
        })

        observeViewModel()
    }

    private fun setupMap() {
        //map.isMyLocationEnabled = true
        map.uiSettings.isCompassEnabled = true
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
    }

    private fun observeViewModel() {
        viewModel.searchResults.observe(this) { results ->
            map.clear()
            for (result in results) {
                map.addMarker(MarkerOptions().position(result.latLng).title(result.name))
            }
        }

        viewModel.userLocation.observe(this) { location ->
            // Update user location on map
        }

        viewModel.route.observe(this) { route ->
            // Draw route on map
        }
    }
}