package com.app.waypoint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.waypoint.core.AppBottomBar
import com.app.waypoint.core.AppNavHost
import com.app.waypoint.ui.theme.WayPointTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WayPointTheme {
                val navHostController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(), bottomBar = {
                        AppBottomBar(navHostController)
                    }) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        AppNavHost(navHostController)
                    }
                }
            }
        }
    }
}
