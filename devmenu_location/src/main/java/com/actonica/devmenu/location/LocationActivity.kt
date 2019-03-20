package com.actonica.devmenu.location

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

internal class LocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private val zoom = 13f
    private lateinit var coordinates: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.location_activity)

        this.title = this.resources.getString(R.string.dmTitleLocation)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.coordinates = intent.getParcelableExtra(LocationItem.INTENT_EXTRA_COORDINATES)

        val mapFragment: SupportMapFragment? =
            this.supportFragmentManager.findFragmentById(R.id.mapView) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap ?: return
        with(googleMap) {
            moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, this@LocationActivity.zoom))
            addMarker(MarkerOptions().position(coordinates))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.finish()
        }
        return super.onOptionsItemSelected(item)
    }
}