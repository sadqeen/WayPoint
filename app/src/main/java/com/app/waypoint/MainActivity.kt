package com.app.waypoint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.waypoint.core.AppBottomBar
import com.app.waypoint.core.AppNavHost
import com.app.waypoint.ui.theme.WayPointTheme
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.AndroidEntryPoint
import kotlin.text.startsWith

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Places.isInitialized()) {
            Places.initializeWithNewPlacesApiEnabled(applicationContext, BuildConfig.MAPS_API_KEY)
        }
        enableEdgeToEdge()
        setContent {
            WayPointTheme {
                val navHostController = rememberNavController()
                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val showBottomBar = currentRoute != null &&
                        !currentRoute.startsWith("addPlace")
                Scaffold(
                    modifier = Modifier.fillMaxSize(), bottomBar = {
                        if(showBottomBar)
                        {
                            AppBottomBar(navHostController)
                        }

                    }) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        AppNavHost(navHostController)
                    }
                }
            }
        }
    }
}
