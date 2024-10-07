package com.example.denemedrac.map

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationRequest
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationServices

class LocationManager(context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    private val _location = MutableLiveData<Location>()
    val location: LiveData<Location> = _location

    private val _speed = MutableLiveData<Float>()
    val speed: LiveData<Float> = _speed

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                _location.value = location
                _speed.value = if (location.hasSpeed()) location.speed * 3.6f else 0f // Convert m/s to km/h
            }
        }
    }
/*
    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            interval = 5000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

 */
}