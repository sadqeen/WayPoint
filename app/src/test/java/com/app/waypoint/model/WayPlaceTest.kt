package com.app.waypoint.model

import org.junit.Assert.assertEquals
import org.junit.Test

class WayPlaceTest {
    @Test
    fun `WayPlace properties are set correctly`() {
        val wayPlace = WayPlace(
            tag = "Test Tag",
            description = "Test Description",
            address = "Test Address",
            latLng = null
        )
        
        assertEquals("Test Tag", wayPlace.tag)
        assertEquals("Test Description", wayPlace.description)
        assertEquals("Test Address", wayPlace.address)
        assertEquals(null, wayPlace.latLng)
    }
}
