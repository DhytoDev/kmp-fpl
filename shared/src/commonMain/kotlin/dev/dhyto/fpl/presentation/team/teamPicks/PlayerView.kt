package dev.dhyto.fpl.presentation.team.teamPicks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

@Composable
fun PlayerView(
    modifier: Modifier,
    photoUrl: String,
    playerName: String,
    playerId: Int,
    gameWeek: Int,
    size: Dp,
) {
    val painterResource = asyncPainterResource(photoUrl)

    val playerSummaryViewModel =
        koinViewModel(vmClass = PlayerSummaryViewModel::class, key = playerId.toString())

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        KamelImage(
            modifier = Modifier.size(size),
            resource = painterResource,
            contentDescription = playerName
        )
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                playerName,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
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