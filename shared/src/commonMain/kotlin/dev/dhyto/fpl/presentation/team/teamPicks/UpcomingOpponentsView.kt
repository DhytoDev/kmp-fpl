package dev.dhyto.fpl.presentation.team.teamPicks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.dhyto.fpl.core.components.shimmerEffect
import dev.dhyto.fpl.core.theme.md_theme_light_error
import dev.dhyto.fpl.core.theme.md_theme_light_outline
import dev.dhyto.fpl.domain.entities.UpcomingOpponent
import dev.dhyto.fpl.presentation.UiState
import dev.dhyto.fpl.presentation.team.PlayerSummaryEvent

@Composable
fun UpcomingOpponentsView(
    playerId: Int,
    eventHandler: (event: PlayerSummaryEvent) -> Unit,
    uiState: UiState<Map<Int, List<UpcomingOpponent>>>,
    gameWeek: Int,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(playerId) {
        eventHandler.invoke(PlayerSummaryEvent.GetThreeUpcomingFixtures(playerId, gameWeek))
    }

    when (uiState) {
        is UiState.ErrorState -> {}
        is UiState.SuccessState -> {
            val upcomingOpponents = uiState.data

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val next = upcomingOpponents.getValue(gameWeek + 1)

                BoxWithConstraints(modifier = modifier) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val weight = if (next.size == 1) 1f else 0.5f

                        next.map {
                            OpponentView(
                                modifier = Modifier.weight(weight),
                                color = it.difficulty!!.colorByDifficulty(),
                                textColor = Color.White,
                                shortName = it.team?.shortName ?: "",
                                lineHeight = 8.sp
                            )
                        }.ifEmpty {
                            OpponentView(
                                modifier = Modifier.weight(weight),
                                lineHeight = 8.sp
                            )
                        }
                    }
                }
                upcomingOpponents.filter {
                    it.key == gameWeek + 2 || it.key == gameWeek + 3
                }.filter {
                    it.key <= 38
                }.map { u ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row {
                            u.value.map {
                                OpponentView(
                                    color = it.difficulty!!.colorByDifficulty(),
                                    textColor = Color.White,
                                    shortName = it.team?.shortName ?: "",
                                    lineHeight = 2.sp,
                                    fontSize = 6.sp
                                )
                            }.ifEmpty {
                                OpponentView(
                                    lineHeight = 2.sp,
                                    fontSize = 6.sp,
                                )
                            }
                        }
                        Text(
                            u.key.toString(), fontSize = 6.sp, lineHeight = 4.sp
                        )
                    }
                }

            }
        }

        else -> {
            LazyRow {
                items(2) {
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(size = 8.dp)).width(4.dp)
                            .height(2.dp).shimmerEffect()
                    )
                }
            }

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