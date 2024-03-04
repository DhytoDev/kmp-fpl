package dev.dhyto.fpl.shared.presentation.root

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import dev.dhyto.fpl.shared.presentation.navigation.Navigation
import dev.dhyto.fpl.shared.presentation.navigation.NavigationRoute
import dev.dhyto.fpl.shared.presentation.navigation.currentRoute
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun RootScreen(navigator: Navigator) {
    Scaffold(
        bottomBar = {
            when (currentRoute(navigator)) {
                NavigationRoute.HomeRoute.route,
                NavigationRoute.MyTeamRoute.route,
                NavigationRoute.OthersRoute.route,
                -> BottomNavigationBar(navigator)
            }
        }) {
        Navigation(navigator)
    }
}

@Composable
private fun BottomNavigationBar(navigator: Navigator) {
    BottomAppBar {
        val items = listOf(
            NavigationRoute.HomeRoute,
            NavigationRoute.MyTeamRoute,
            NavigationRoute.OthersRoute
        )

        items.forEach {
            NavigationBarItem(label = { Text(it.title) },
                selected = it.route == currentRoute(navigator),
                icon = it.navIcon,
                onClick = {
                    navigator.navigate(
                        it.route,
                        NavOptions(launchSingleTop = true),
                    )
                }
            )
        }
    }
}