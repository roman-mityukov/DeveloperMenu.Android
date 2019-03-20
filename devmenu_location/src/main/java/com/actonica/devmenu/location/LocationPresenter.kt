package com.actonica.devmenu.location

internal interface LocationPresenterInput {
    fun attachOutput(output: LocationPresenterOutput?)
    fun detachOutput()
    fun onStart()
}

internal interface LocationPresenterOutput {
    fun onChange(model: LocationModel)
}

internal class LocationPresenter(
    private var output: LocationPresenterOutput?,
    private val locationProvider: LocationProvider
) : LocationPresenterInput {
    override fun attachOutput(output: LocationPresenterOutput?) {
        this.output = output
    }

    override fun detachOutput() {
        this.output = null
    }

    override fun onStart() {
        this.locationProvider.getLocation(listener = this::onLocationStateReceived)
    }

    private fun onLocationStateReceived(model: LocationModel.State) {
        this.output?.onChange(model)
    }
}