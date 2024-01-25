package dev.dhyto.fpl.shared.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

sealed class NavigationRoute(
    val route: String,
    val title: String = "",
    val navIcon: (@Composable () -> Unit) = {
        Icon(
            Icons.Filled.Home, contentDescription = "home"
        )
    },
    val objectName: String = "",
    val objectPath: String = "",
) {
    data object HomeRoute : NavigationRoute("home")
    data object MyTeamRoute : NavigationRoute("my-team")
    data object OthersRoute : NavigationRoute("others")
}