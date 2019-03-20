package com.actonica.devmenu.location

sealed class LocationModel {
    data class State(
        val latitude: Double?,
        val longitude: Double?,
        val time: String,
        val provider: String,
        val accuracy: String
    ) : LocationModel() {
        override fun toString(): String {
            return "Location" +
                    "\nlatitude = ${this.latitude}" +
                    "\nlongitude = ${this.longitude}" +
                    "\ntime = ${this.time}" +
                    "\nprovider = ${this.provider}" +
                    "\naccuracy = ${this.accuracy}"
        }
    }
}