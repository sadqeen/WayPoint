package com.app.waypoint.core

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.waypoint.model.WayPlace
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PreferenceManagerTest {

    private lateinit var preferenceManager: PreferenceManager

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        preferenceManager = PreferenceManager(context)
    }

    @Test
    fun saveAndGetWayPlaces() {
        val place = WayPlace("Home", "My House", "123 St", null)
        preferenceManager.saveWayPlace(place)
        
        val places = preferenceManager.getWayPlaces()
        // We check if the saved place is present. 
        // Note: Since it's persistent, previous runs might have data.
        val found = places.any { it.tag == "Home" && it.address == "123 St" }
        assertEquals(true, found)
    }
}
