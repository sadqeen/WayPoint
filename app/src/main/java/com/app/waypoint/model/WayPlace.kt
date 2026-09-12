package com.app.waypoint.model

import com.google.android.gms.maps.model.LatLng

data class WayPlace(val tag: String, val description : String, val address: String, val latLng: LatLng?) {
}