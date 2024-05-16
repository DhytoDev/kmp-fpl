package dev.dhyto.fpl.presentation.team.teamPicks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.dhyto.fpl.presentation.team.PlayerSummaryViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerView(
    modifier: Modifier = Modifier,
    photoUrl: String,
    playerName: String,
    playerId: Int,
    gameWeek: Int,
    size: Dp,
    isCaptain: Boolean = false,
    isViceCaptain: Boolean = false,
    onPlayerClick: () -> Unit = {}
) {
    val painterResource = asyncPainterResource(photoUrl)

    val playerSummaryViewModel =
        koinViewModel(vmClass = PlayerSummaryViewModel::class, key = playerId.toString())

    Box(
        modifier = Modifier.width(size).clickable {
           onPlayerClick()
        }
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BadgedBox(
                badge = {
                    if (isCaptain || isViceCaptain) {
                        Badge(
                            modifier = Modifier.padding(2.dp),
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                if (isCaptain) "C" else "V",
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            ) {
                KamelImage(
                    modifier = Modifier.size(size),
                    resource = painterResource,
                    contentDescription = playerName
                )
            }

            Box(
                modifier = Modifier
                    .width(size)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    playerName,
                    modifier = Modifier
                        .basicMarquee(iterations = Int.MAX_VALUE)
                        .align(Alignment.Center),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                )
            }

            UpcomingOpponentsView(
                playerId = playerId,
                eventHandler = playerSummaryViewModel::handleEvent,
                uiState = playerSummaryViewModel.state.collectAsStateWithLifecycle().value,
                gameWeek = gameWeek,
                modifier = Modifier.width(size)
            )
        }
    }
}