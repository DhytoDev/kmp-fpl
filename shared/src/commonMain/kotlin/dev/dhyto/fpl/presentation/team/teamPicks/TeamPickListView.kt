package dev.dhyto.fpl.presentation.team.teamPicks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger
import dev.dhyto.fpl.core.components.shimmerEffect
import dev.dhyto.fpl.core.theme.md_theme_light_error
import dev.dhyto.fpl.core.theme.md_theme_light_outline
import dev.dhyto.fpl.domain.base.Failure
import dev.dhyto.fpl.domain.entities.ManagerEntry
import dev.dhyto.fpl.domain.entities.UpcomingOpponent
import dev.dhyto.fpl.presentation.UiState
import dev.dhyto.fpl.presentation.navigation.NavigationRoute
import dev.dhyto.fpl.presentation.team.MyTeamEvent
import dev.dhyto.fpl.presentation.team.PlayerSummaryEvent
import dev.dhyto.fpl.presentation.team.PlayerSummaryViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
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
                        modifier = Modifier
                            .padding(vertical = 6.dp, horizontal = 2.dp),
                        photoUrl = state.data[i].player.photoUrl,
                        playerName = state.data[i].player.displayName,
                        playerId = state.data[i].player.id!!,
                        gameWeek = gameWeek
                    )

                }
            }
        }
    }
}

@Composable
fun PlayerView(
    modifier: Modifier,
    photoUrl: String,
    playerName: String,
    playerId: Int,
    gameWeek: Int
) {
    val painterResource = asyncPainterResource(photoUrl)

    val playerSummaryViewModel =
        koinViewModel(vmClass = PlayerSummaryViewModel::class, key = playerId.toString())

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        KamelImage(
            modifier = Modifier.size(60.dp),
            resource = painterResource,
            contentDescription = playerName
        )
        Box(
            modifier = Modifier
                .background(colorScheme.surfaceVariant)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                playerName,
                fontSize = 12.sp,
                color = colorScheme.onSurfaceVariant
            )
        }
        UpcomingOpponentsView(
            playerId = playerId,
            eventHandler = playerSummaryViewModel::handleEvent,
            uiState = playerSummaryViewModel.state.collectAsStateWithLifecycle().value,
            gameWeek = gameWeek,
        )
    }
}

@Composable
fun UpcomingOpponentsView(
    playerId: Int,
    eventHandler: (event: PlayerSummaryEvent) -> Unit,
    uiState: UiState<Map<Int, List<UpcomingOpponent>>>,
    gameWeek: Int
) {
    LaunchedEffect(playerId) {
        eventHandler.invoke(PlayerSummaryEvent.GetThreeUpcomingFixtures(playerId, gameWeek))
        Logger.d("API Called : $playerId")
    }

    LazyRow {
        when (uiState) {
            is UiState.ErrorState -> {}
            is UiState.SuccessState -> {
                val upcomingOpponents = uiState.data

                upcomingOpponents.map { u ->
                    item {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                u.value.map {
                                    OpponentView(
                                        color = it.difficulty!!.colorByDifficulty(),
                                        textColor = Color.White,
                                        shortName = it.team?.shortName ?: ""
                                    )
                                }.ifEmpty {
                                    OpponentView(
                                        color = Color.White,
                                        textColor = Color.Black
                                    )
                                }
                            }
                            Text(
                                u.key.toString(),
                                fontSize = 8.sp,
                                lineHeight = 4.sp
                            )
                        }

                    }
                }
            }

            else -> {
                items(2) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(size = 8.dp))
                            .width(4.dp)
                            .height(2.dp)
                            .shimmerEffect()
                    )
                }
            }
        }
    }
}

@Composable
internal fun OpponentView(
    color: Color,
    textColor: Color,
    shortName: String = "BLANK",
) {
    Box(
        modifier = Modifier.background(color).padding(horizontal = 2.dp)
    ) {
        Text(
            shortName,
            fontSize = 8.sp,
            color = textColor,
            lineHeight = 4.sp
        )
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

fun Int.colorByDifficulty(): Color {
    return when (this) {
        1 -> Color.Green
        2 -> Color.Green.copy(green = 0.8f)
        3 -> md_theme_light_outline
        4 -> md_theme_light_error.copy(red = 1.0f)
        5 -> md_theme_light_error
        else -> Color.White
    }
}