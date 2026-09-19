package com.app.waypoint.screens

import com.app.waypoint.core.PreferenceManager
import com.app.waypoint.model.WayPlace
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class AddPlaceViewModelTest {

    private lateinit var viewModel: AddPlaceViewModel
    private val preferenceManager: PreferenceManager = mockk(relaxed = true)

    @Before
    fun setup() {
        viewModel = AddPlaceViewModel(preferenceManager)
    }

    @Test
    fun `savePlace should call preferenceManager saveWayPlace`() {
        // Given
        val place = WayPlace(
            tag = "Work",
            description = "Office",
            address = "123 Main St",
            latLng = null
        )

        // When
        viewModel.savePlace(place)

        // Then
        verify { preferenceManager.saveWayPlace(place) }
    }
}
