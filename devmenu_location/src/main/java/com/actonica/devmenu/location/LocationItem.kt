package com.actonica.devmenu.location

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.actonica.devmenu.contract.DefaultDevMenuItem
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.location_view.view.*

/**
 * Shows current geolocation
 * - latitude
 * - longitude
 * - time
 * - provider
 * - accuracy
 */
class LocationItem(private val applicationContext: Context) : DefaultDevMenuItem(),
    LocationPresenterOutput {

    companion object {
        const val INTENT_EXTRA_COORDINATES: String = "coordinates"
    }

    private lateinit var view: View
    private lateinit var presenterInput: LocationPresenterInput
    override var model: LocationModel.State? = null

    override fun getView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View {
        this.view = layoutInflater.inflate(R.layout.location_view, viewGroup, false)
        this.view.latitude.text = viewGroup.context.getString(R.string.dmLabelLatitude)
        this.view.longitude.text = viewGroup.context.getString(R.string.dmLabelLongitude)
        this.view.time.text = viewGroup.context.getString(R.string.dmLabelTime)
        this.view.provider.text = viewGroup.context.getString(R.string.dmLabelProvider)
        this.view.accuracy.text = viewGroup.context.getString(R.string.dmLabelAccuracy)

        this.view.buttonShowOnMap.setOnClickListener {
            if (this.model?.latitude != null && this.model?.longitude != null) {
                val intent = Intent(viewGroup.context, LocationActivity::class.java)
                intent.putExtra(
                    INTENT_EXTRA_COORDINATES,
                    LatLng(this.model?.latitude!!, this.model?.longitude!!)
                )
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                viewGroup.context.startActivity(intent)
            } else {
                Toast.makeText(viewGroup.context, "No coordinates", Toast.LENGTH_LONG).show()
            }
        }

        this.presenterInput = LocationPresenter(this, LocationProvider(this.applicationContext))
        presenterInput.onStart()

        return view
    }

    override fun onStart() {
        super.onStart()
        if (this::presenterInput.isInitialized) {
            this.presenterInput.onStart()
        }
    }

    override fun onChange(model: LocationModel) {
        when (model) {
            is LocationModel.State -> {
                this.model = model
                if (this::view.isInitialized) {
                    this.view.latitudeValue.text = model.latitude?.toString() ?: "n/a"
                    this.view.longitudeValue.text = model.longitude?.toString() ?: "n/a"
                    this.view.timeValue.text = model.time
                    this.view.providerValue.text = model.provider
                    this.view.accuracyValue.text = model.accuracy
                }
            }
        }
    }
}