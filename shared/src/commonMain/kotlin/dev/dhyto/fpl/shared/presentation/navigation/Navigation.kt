package dev.dhyto.fpl.shared.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import dev.dhyto.fpl.shared.presentation.dreamTeam.DreamTeamAndFixturesViewModel
import dev.dhyto.fpl.shared.presentation.home.HomeScreen
import dev.dhyto.fpl.shared.presentation.summary.ManagerInfoViewModel
import dev.dhyto.fpl.shared.presentation.team.MyTeamScreen
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun Navigation(navigator: Navigator) {
    NavHost(
        navigator = navigator,
        initialRoute = NavigationRoute.HomeRoute.route,
    ) {
        scene(route = NavigationRoute.HomeRoute.route) {
            val dreamTeamAndFixturesViewModel =
                koinViewModel(vmClass = DreamTeamAndFixturesViewModel::class)

            val managerInfoViewModel = koinViewModel(vmClass = ManagerInfoViewModel::class)

            HomeScreen(
                navigator = navigator,
                dreamTeamAndFixturesViewModel = dreamTeamAndFixturesViewModel,
                managerInfoViewModel = managerInfoViewModel,
            )
        }
        scene(route = NavigationRoute.MyTeamRoute.route) {
            MyTeamScreen(navigator)
        }
        scene(route = NavigationRoute.OthersRoute.route) {
        }
    }
}

@Composable
fun currentRoute(navigator: Navigator): String? =
    navigator.currentEntry.collectAsState(null).value?.route?.route