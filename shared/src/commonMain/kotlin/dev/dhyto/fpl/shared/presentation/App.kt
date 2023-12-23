package dev.dhyto.fpl.shared.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.dhyto.fpl.shared.presentation.dreamTeam.DreamTeamAndFixturesViewModel
import dev.dhyto.fpl.shared.presentation.dreamTeam.DreamTeamSection
import dev.dhyto.fpl.shared.presentation.dreamTeam.PlayersAndFixtures
import dev.dhyto.fpl.shared.presentation.fixtures.FixturesSection
import dev.dhyto.fpl.shared.presentation.summary.SummaryCard
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
                        val dreamTeamAndFixturesViewModel =
                            koinViewModel(vmClass = DreamTeamAndFixturesViewModel::class)

                        HomeScreen(
                            dreamTeamAndFixturesViewModel = dreamTeamAndFixturesViewModel,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    dreamTeamAndFixturesViewModel: DreamTeamAndFixturesViewModel,
) {
    LaunchedEffect(Unit) {
        dreamTeamAndFixturesViewModel.getDreamTeamSquad()
    }

    val uiState: UiState<PlayersAndFixtures> by dreamTeamAndFixturesViewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.padding(16.dp)
    ) {
        Column {
            SummaryCard(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth().padding(bottom = 16.dp)
            )
            FixturesSection(
                fixturesUiState = uiState,
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            )
            DreamTeamSection(uiState)
        }
    }
}

