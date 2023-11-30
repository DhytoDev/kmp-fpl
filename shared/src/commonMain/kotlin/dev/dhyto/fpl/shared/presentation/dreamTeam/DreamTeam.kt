package dev.dhyto.fpl.shared.presentation.dreamTeam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.dhyto.fpl.shared.domain.entities.Player
import dev.dhyto.fpl.shared.presentation.UiState
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun DreamTeamSection(uiState: UiState<List<Player>>) {
    when (uiState) {
        is UiState.ErrorState -> Text(text = uiState.message)
        is UiState.InitialState -> CircularProgressIndicator()
        is UiState.LoadingState -> CircularProgressIndicator()
        is UiState.SuccessState<List<Player>> -> Column {
            Text(
                "Dream Team",
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow {
                itemsIndexed(uiState.data) { _, player ->
                    DreamTeamCardView(
                        player, modifier = Modifier.padding(end = 8.dp).width(150.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun DreamTeamCardView(
    player: Player,
    modifier: Modifier,
) {
    val painterPlayerResource = asyncPainterResource(player.photoUrl)
    val painterBadgeResource = asyncPainterResource(player.team.teamBadgeUrl)

    Box(modifier = modifier) {
        Column {
            Card(
                modifier = Modifier.width(150.dp)
            ) {
                Row {
                    KamelImage(
                        modifier = Modifier.height(50.dp).width(50.dp)
                            .padding(start = 8.dp, top = 4.dp),
                        resource = painterBadgeResource,
                        contentDescription = player.team.name,
                        contentScale = ContentScale.Fit,
                        contentAlignment = Alignment.TopStart,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    KamelImage(
                        modifier = Modifier.height(100.dp).width(100.dp),
                        resource = painterPlayerResource,
                        contentDescription = player.name,
                        contentScale = ContentScale.Fit,
                        contentAlignment = Alignment.BottomEnd,
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                player.name,
                style = MaterialTheme.typography.titleSmall,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${player.points} pts",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Box (
                    modifier = Modifier
                        .clip(RoundedCornerShape(size = 4.dp))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                        .defaultMinSize(minWidth = 20.dp)
                ) {
                    Text(
                        player.getPosition(),
                        style = MaterialTheme.typography.bodySmall,
                        )
                }
            }
        }
    }
}