package dev.dhyto.fpl.shared.presentation

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
import dev.dhyto.fpl.shared.domain.entities.Fixture
import dev.dhyto.fpl.shared.domain.entities.Player
import dev.dhyto.fpl.shared.presentation.dreamTeam.DreamTeamSection
import dev.dhyto.fpl.shared.presentation.dreamTeam.DreamTeamViewModel
import dev.dhyto.fpl.shared.presentation.fixtures.FixturesSection
import dev.dhyto.fpl.shared.presentation.fixtures.FixturesViewModel
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
                        val dreamTeamViewModel = koinViewModel(vmClass = DreamTeamViewModel::class)
                        val fixturesViewModel = koinViewModel(vmClass = FixturesViewModel::class)

                        HomeScreen(
                            dreamTeamViewModel = dreamTeamViewModel,
                            fixturesViewModel = fixturesViewModel,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    dreamTeamViewModel: DreamTeamViewModel,
    fixturesViewModel: FixturesViewModel,
) {
    LaunchedEffect(Unit) {
        dreamTeamViewModel.getDreamTeamSquad()
        fixturesViewModel.getCurrentFixtures()
    }


    val dreamTeamUiState: UiState<List<Player>> by dreamTeamViewModel.state.collectAsStateWithLifecycle()
    val fixturesUiState: UiState<List<Fixture>> by fixturesViewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.padding(16.dp)
    ) {
        Column {
            DreamTeamSection(dreamTeamUiState)
            FixturesSection(
                fixturesUiState = fixturesUiState,
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            )
        }
    }
}

