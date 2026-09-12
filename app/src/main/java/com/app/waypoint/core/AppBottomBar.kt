package com.app.waypoint.core

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.app.waypoint.model.BottomNavItems
import com.app.waypoint.R


@Composable
fun AppBottomBar(navHostController: NavHostController) {
    val bottomNavItems = listOf(
        BottomNavItems("Places", "Places", R.drawable.ic_places),
        BottomNavItems("addPlace", "Add", R.drawable.ic_add_v2),
        BottomNavItems("History", "History", R.drawable.ic_history)
    )
    val navBackStackEntry = navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar {
        bottomNavItems.forEach { item ->
            val isAdd = item.route == "addPlace"
            NavigationBarItem(
                selected = item.route == currentRoute,
                onClick = {
                    navHostController.navigate(item.route) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }

                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        modifier = if (isAdd) Modifier.size(48.dp) else Modifier.size(24.dp),
                        tint = if (isAdd) Color.Unspecified else LocalContentColor.current
                    )
                },
                label = if (isAdd) null else {
                    { Text(item.label) }
                },
                alwaysShowLabel = !isAdd
            )
        }
    }
}
