package com.example.denemedrac.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

    private val _searchResults = MutableLiveData<List<PlaceResult>>()
    val searchResults: LiveData<List<PlaceResult>> = _searchResults

    private val _userLocation = MutableLiveData<LatLng>()
    val userLocation: LiveData<LatLng> = _userLocation

    private val _route = MutableLiveData<Route>()
    val route: LiveData<Route> = _route

    fun searchPlaces(query: String) {
        viewModelScope.launch {
            // Implement place search logic here
            // Update _searchResults with the results
        }
    }

    fun updateUserLocation(location: LatLng) {
        _userLocation.value = location
    }

    fun getDirections(destination: LatLng) {
        viewModelScope.launch {
            // Implement route calculation logic here
            // Update _route with the calculated route
        }
    }
}

data class PlaceResult(val name: String, val latLng: LatLng)
data class Route(val points: List<LatLng>)