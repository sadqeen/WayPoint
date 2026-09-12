package com.app.waypoint.core

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.waypoint.screens.AddPlace

@Composable
fun AppNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = "Places"
    ) {
        composable("Places") {
            Text(text = "Places Screen")
        }
        composable("addPlace") {
            AddPlace(navHostController)
        }
        composable("History") {
            Text(text = "History Screen")
        }
    }
}
