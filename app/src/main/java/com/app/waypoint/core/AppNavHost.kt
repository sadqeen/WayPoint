package com.app.waypoint.core

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = "Places"
    ) {
        composable("Places") {
            Text(text = "Places Screen")
        }
        composable("add") {
            Text(text = "Add Screen")
        }
        composable("History") {
            Text(text = "History Screen")
        }
    }
}
