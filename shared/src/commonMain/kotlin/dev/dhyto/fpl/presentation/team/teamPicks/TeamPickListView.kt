package dev.dhyto.fpl.presentation.team.teamPicks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.dhyto.fpl.domain.base.Failure
import dev.dhyto.fpl.domain.entities.ManagerEntry
import dev.dhyto.fpl.presentation.UiState
import dev.dhyto.fpl.presentation.navigation.NavigationRoute
import dev.dhyto.fpl.presentation.team.MyTeamEvent
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TeamPickListView(
    state: UiState<List<ManagerEntry>>,
    eventHandler: (event: MyTeamEvent) -> Unit,
    navigator: Navigator,
    gameWeek: Int,
) {
    when (state) {
        is UiState.ErrorState -> {
            if (state.failure is Failure.UnauthenticatedFailure) {
                UnauthenticatedLayout(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        navigator.navigate(
                            NavigationRoute.SignInRoute.route,
                            NavOptions(launchSingleTop = true)
                        )
                    }
                )
            }
        }

        UiState.InitialState -> {}
        UiState.LoadingState -> {
            CircularProgressIndicator()
        }

        is UiState.SuccessState<List<ManagerEntry>> -> {
            BoxWithConstraints {
                val size = 60 * 720 / constraints.maxWidth

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    maxItemsInEachRow = 5,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    for (i in state.data.indices) {
                        if (i in 1..11 &&
                            state.data[i].player.elementType != state.data[i - 1].player.elementType
                        ) {
                            Spacer(Modifier.fillMaxWidth())
                        }


                        PlayerView(
                            modifier = Modifier.padding(top = 4.dp, end = 2.dp),
                            photoUrl = state.data[i].player.photoUrl,
                            playerName = state.data[i].player.displayName,
                            playerId = state.data[i].player.id!!,
                            gameWeek = gameWeek,
                            size = size.dp,
                            isCaptain = state.data[i].isCaptain,
                            isViceCaptain = state.data[i].isViceCaptain
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UnauthenticatedLayout(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
    ) {
        Text("Sign In Required")
        Spacer(Modifier.height(8.dp))
        Button(onClick = onClick) {
            Text("Sign In Now")
        }
    }
}