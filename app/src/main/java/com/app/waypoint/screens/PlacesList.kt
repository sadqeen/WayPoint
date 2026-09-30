package com.app.waypoint.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun PlacesList(
    navHostController: NavHostController,
    viewModel: AddPlaceViewModel = hiltViewModel(),
) {
    val allPlaces = viewModel.getAllPlaces()
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(
            items = allPlaces,
            key = { place -> place.tag },
        ) { place ->
            PlacesRow(
                wayPlace = place,
            ) {
                // Action when clicking a place item
            }
        }
    }
}
