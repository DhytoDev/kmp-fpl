package dev.dhyto.fpl.shared.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.dhyto.fpl.shared.core.theme.AppTheme
import dev.dhyto.fpl.shared.domain.entities.ManagerInfo
import dev.dhyto.fpl.shared.domain.entities.Player
import dev.dhyto.fpl.shared.presentation.dreamTeam.DreamTeamAndFixturesViewModel
import dev.dhyto.fpl.shared.presentation.dreamTeam.DreamTeamSection
import dev.dhyto.fpl.shared.presentation.dreamTeam.PlayersAndFixtures
import dev.dhyto.fpl.shared.presentation.fixtures.FixturesSection
import dev.dhyto.fpl.shared.presentation.summary.ManagerInfoViewModel
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
            AppTheme {
                NavHost(
                    navigator = navigator, initialRoute = "/"
                ) {
                    scene(route = "/") {
                        val dreamTeamAndFixturesViewModel =
                            koinViewModel(vmClass = DreamTeamAndFixturesViewModel::class)

                        val managerInfoViewModel =
                            koinViewModel(vmClass = ManagerInfoViewModel::class)

                        HomeScreen(
                            dreamTeamAndFixturesViewModel = dreamTeamAndFixturesViewModel,
                            managerInfoViewModel = managerInfoViewModel,
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
    managerInfoViewModel: ManagerInfoViewModel,
) {
    LaunchedEffect(Unit) {
        dreamTeamAndFixturesViewModel.getDreamTeamSquad()
        managerInfoViewModel.getManagerInfo()
    }

    val uiState: UiState<PlayersAndFixtures> by dreamTeamAndFixturesViewModel.state.collectAsStateWithLifecycle()
    val managerInfoUiState by managerInfoViewModel.state.collectAsStateWithLifecycle()

    var managerInfo = ManagerInfo()

    Scaffold {
        Column {
            if (managerInfoUiState is UiState.SuccessState<ManagerInfo>) {
                managerInfo = (managerInfoUiState as UiState.SuccessState<ManagerInfo>).data
            }

            SummaryCard(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                managerInfo = managerInfo,
            )
            FixturesSection(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
                showLoading = uiState is UiState.LoadingState,
                fixtures = (uiState as? UiState.SuccessState)?.data?.fixtures ?: emptyList()
            )
            DreamTeamSection(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                showLoading = uiState is UiState.LoadingState,
                players = (uiState as? UiState.SuccessState)?.data?.players ?: Player.dummyPlayers()
            )
        }
    }
}

