package com.app.waypoint.screens

import androidx.lifecycle.ViewModel
import com.app.waypoint.core.PreferenceManager
import com.app.waypoint.model.WayPlace
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddPlaceViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    fun savePlace(wayPlace: WayPlace) {
        preferenceManager.saveWayPlace(wayPlace)
    }
}
