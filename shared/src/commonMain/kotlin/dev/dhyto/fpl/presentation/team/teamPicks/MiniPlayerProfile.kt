package dev.dhyto.fpl.presentation.team.teamPicks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Copyright
import androidx.compose.material.icons.filled.SwitchLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.dhyto.fpl.domain.entities.Player

@Composable
fun MiniPlayerProfile(
    modifier: Modifier = Modifier,
    player: Player,
    onSubsClick: () -> Unit = {},
    onCaptainClick: () -> Unit = {},
    onViceCaptainClick: () -> Unit = {},
) {
    Column(modifier) {
        Text(player.displayName)
        Spacer(Modifier.height(8.dp))
        TeamPicksActionTile(
            modifier = Modifier.clickable(onClick = onSubsClick),
            text = "Substitute",
            leading = {
                Icon(
                    Icons.Filled.SwitchLeft,
                    contentDescription = null
                )
            },
        )
        TeamPicksActionTile(
            modifier = Modifier.clickable { onCaptainClick() },
            text = "Make as Captain",
            leading = {
                Icon(
                    Icons.Filled.Copyright,
                    contentDescription = null
                )
            },
        )
        TeamPicksActionTile(
            modifier = Modifier.clickable { onViceCaptainClick() },
            text = "Make as Vice Captain",
            leading = {
                Icon(
                    Icons.Filled.Copyright,
                    contentDescription = null
                )
            },
        )
        Spacer(Modifier.height(8.dp))
    }
}