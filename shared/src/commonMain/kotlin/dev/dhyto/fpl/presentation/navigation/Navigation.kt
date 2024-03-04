package dev.dhyto.fpl.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.dhyto.fpl.presentation.dreamTeam.DreamTeamAndFixturesViewModel
import dev.dhyto.fpl.presentation.home.HomeScreen
import dev.dhyto.fpl.presentation.login.SignInScreen
import dev.dhyto.fpl.presentation.login.SignInViewModel
import dev.dhyto.fpl.presentation.summary.ManagerInfoViewModel
import dev.dhyto.fpl.presentation.team.MyTeamScreen
import dev.dhyto.fpl.presentation.team.MyTeamViewModel
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
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
            val myTeamViewModel = koinViewModel(vmClass = MyTeamViewModel::class)

            MyTeamScreen(
                navigator = navigator,
                myTeamViewModel = myTeamViewModel
            )
        }
        scene(route = NavigationRoute.OthersRoute.route) {
        }

        scene(route = NavigationRoute.SignInRoute.route) {
            val authViewModel = koinViewModel(SignInViewModel::class)

            SignInScreen(
                modifier = Modifier.padding(horizontal = 16.dp),
                state = authViewModel.uiState.collectAsState().value,
                eventHandler = authViewModel::handleEvent,
                navigator = navigator,
            )
        }
    }
}

@Composable
fun currentRoute(navigator: Navigator): String? =
    navigator.currentEntry.collectAsState(null).value?.route?.route