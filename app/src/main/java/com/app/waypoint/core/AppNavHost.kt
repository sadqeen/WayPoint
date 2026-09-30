package com.app.waypoint.core

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.waypoint.screens.AddPlace
import com.app.waypoint.screens.PlacesList

@Composable
fun AppNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = "Places",
    ) {
        composable("Places") {
            PlacesList(navHostController)
        }
        composable("addPlace") {
            AddPlace(navHostController)
        }
        composable("History") {
            Text(text = "History Screen")
        }
    }
}
