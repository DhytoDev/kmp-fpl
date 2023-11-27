package dev.dhyto.fpl.shared.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.dhyto.fpl.shared.domain.entities.Player
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.KoinContext

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    KoinContext {
        PreComposeApp {
            val navigator = rememberNavigator()
            MaterialTheme {
                NavHost(
                    navigator = navigator,
                    initialRoute = "/"
                ) {
                    scene(route = "/") {
                        val viewModel = koinViewModel(vmClass = dev.dhyto.fpl.shared.presentation.DreamTeamViewModel::class)

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
        viewModel.getDreamTeamSquad(13)
    }

    when (val resourceState = viewModel.theDreamTeams) {
        is ResourceState.ErrorState -> Text(text = resourceState.message)
        is ResourceState.InitialState -> CircularProgressIndicator()
        is ResourceState.LoadingState -> CircularProgressIndicator()
        is ResourceState.SuccessState<List<Player>> -> Column {
            Text("Dream Team")
            LazyRow {
                itemsIndexed(resourceState.data) { _, player ->
                    DreamTeamCardView(playerName = player.name)
                }
            }
        }
    }
}

@Composable
fun DreamTeamCardView(
    playerName: String,
) {
    Card(
        modifier = Modifier
            .padding(end = 8.dp)
            .size(100.dp)
    ) {
        Column {
            Text(playerName)
        }
    }
}