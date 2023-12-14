package dev.dhyto.fpl.shared.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.dhyto.fpl.shared.domain.entities.Player
import dev.dhyto.fpl.shared.presentation.dreamTeam.DreamTeamSection
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.compose.KoinContext

@Composable
fun App() {
    KoinContext {
        PreComposeApp {
            val navigator = rememberNavigator()
            MaterialTheme {
                NavHost(
                    navigator = navigator, initialRoute = "/"
                ) {
                    scene(route = "/") {
                        val viewModel = koinViewModel(vmClass = DreamTeamViewModel::class)

                        HomeScreen(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: DreamTeamViewModel) {
    LaunchedEffect(Unit) {
        viewModel.getDreamTeamSquad()
    }

    val uiState: UiState<List<Player>> by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.padding(16.dp)
    ) {
        Column {
            DreamTeamSection(uiState)
        }
    }
}

