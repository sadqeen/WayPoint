package com.app.waypoint.screens

import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import org.junit.Rule
import org.junit.Test

class AddPlaceUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testAddPlaceContentDisplays() {
        composeTestRule.setContent {
            AddPlaceContent(
                searchQuery = "",
                placeTag = "",
                errorDescription = null,
                selectedLocation = null,
                cameraPositionState = remember {
                    CameraPositionState(
                        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 16f)
                    )
                },
                hasLocationPermission = false,
                onPlaceTagChange = {},
                onSearchClick = {},
                onCancelClick = {},
                onSaveClick = {}
            )
        }

        // Verify that the UI elements are displayed
        composeTestRule.onNodeWithText("Enter Place Name").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cancel").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add Place").assertIsDisplayed()
    }
}
