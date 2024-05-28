package dev.dhyto.fpl.presentation.team.teamPicks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.dhyto.fpl.domain.entities.Player
import dev.dhyto.fpl.presentation.team.PlayerSummaryViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerView(
    modifier: Modifier = Modifier,
    player: Player,
    gameWeek: Int,
    size: Dp,
    isCaptain: Boolean = false,
    isViceCaptain: Boolean = false,
    onPlayerClick: () -> Unit = {},
    playerToSub: Player? = null,
    isPotentialSub: Boolean = false,
    position: Int = 0
) {
    val painterResource = asyncPainterResource(player.photoUrl)

    val roundedShape = Shapes().extraSmall

    val playerSummaryViewModel =
        koinViewModel(vmClass = PlayerSummaryViewModel::class, key = player.id.toString())

    val (color, cardModifier) = remember(playerToSub) {
        when {
            playerToSub?.id == player.id -> Pair(
                Color.Green,
                Modifier
                    .border(
                        width = 2.dp,
                        shape = roundedShape,
                        color = Color.Green.copy(alpha = 0.5f)
                    )
                    .background(Color.White.copy(alpha = 0.2f))
            )

            isPotentialSub -> Pair(
                Color.Red,
                Modifier
                    .border(
                        width = 2.dp,
                        shape = roundedShape,
                        color = Color.Red.copy(alpha = 0.5f)
                    )
                    .background(Color.White.copy(alpha = 0.2f))
            )

            else -> Pair(
                Color.Gray,
                Modifier,
            )
        }
    }


    Column(
        modifier = modifier.clickable { onPlayerClick() }.then(cardModifier),
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
                contentDescription = player.displayName,
            )
        }

        Box(
            modifier = Modifier
                .width(size)
                .background(color)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                player.name + ": $position",
                modifier = Modifier
                    .basicMarquee(iterations = Int.MAX_VALUE)
                    .align(Alignment.Center),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
            )
        }

        UpcomingOpponentsView(
            playerId = player.id!!,
            eventHandler = playerSummaryViewModel::handleEvent,
            uiState = playerSummaryViewModel.state.collectAsStateWithLifecycle().value,
            gameWeek = gameWeek,
            modifier = Modifier.width(size)
        )
    }
}