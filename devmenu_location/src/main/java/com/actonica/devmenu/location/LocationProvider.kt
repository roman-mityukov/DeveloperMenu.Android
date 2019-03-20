package com.actonica.devmenu.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import java.text.SimpleDateFormat
import java.util.Locale

internal class LocationProvider(private val applicationContext: Context) {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var listener: (LocationModel.State) -> Unit
    private var priority = 0
    private val updateInterval = 5000L // milliseconds

    private val locationCallback: LocationCallback = object : LocationCallback() {
        @SuppressLint("SetTextI18n")
        override fun onLocationResult(locationResult: LocationResult) {
            if (locationResult.locations.size > 0) {
                this@LocationProvider.fusedLocationProviderClient.removeLocationUpdates(this)
                val location = locationResult.locations[0]

                if (this@LocationProvider::listener.isInitialized) {
                    listener.invoke(
                        LocationModel.State(
                            latitude = location.latitude,
                            longitude = location.longitude,
                            time = SimpleDateFormat("HH:mm:ss dd.MM.yyyy", Locale.US).format(
                                location.time
                            ),
                            provider = location.provider,
                            accuracy = "${location.accuracy}m"
                        )
                    )
                }
            }
        }
    }

    fun getLocation(listener: (LocationModel.State) -> Unit) {
        this.listener = listener
        val isCoarseLocationGranted: Boolean = ContextCompat.checkSelfPermission(
            this.applicationContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val isFineLocationGranted: Boolean = ContextCompat.checkSelfPermission(
            this.applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (isCoarseLocationGranted || isFineLocationGranted) {

            this.priority = when {
                isFineLocationGranted -> LocationRequest.PRIORITY_HIGH_ACCURACY
                isCoarseLocationGranted -> LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
                else -> 0
            }

            request()
        } else {
            this.listener.invoke(
                LocationModel.State(
                    latitude = null,
                    longitude = null,
                    time = "n/a",
                    provider = "n/a",
                    accuracy = "n/a"
                )
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun request() {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = priority
        locationRequest.interval = updateInterval
        locationRequest.fastestInterval = updateInterval

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(this.applicationContext)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            this.fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this.applicationContext)

            this.fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                if (priority == LocationRequest.PRIORITY_HIGH_ACCURACY) {
                    this.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
                    request()
                } else if (priority == LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY) {
                    exception.printStackTrace()
                    this.listener.invoke(
                        LocationModel.State(
                            latitude = null,
                            longitude = null,
                            time = "n/a",
                            provider = "n/a",
                            accuracy = "n/a"
                        )
                    )
                }
            }
        }
    }
}