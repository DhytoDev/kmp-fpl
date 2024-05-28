package dev.dhyto.fpl.presentation.team.teamPicks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.dhyto.fpl.domain.entities.ManagerEntry
import dev.dhyto.fpl.domain.entities.Player

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TeamPicksBody(
    modifier: Modifier = Modifier,
    starters: List<ManagerEntry>,
    substitutes: List<ManagerEntry>,
    size: Dp,
    gameWeek: Int,
    selectPlayer: (player: Player) -> Unit,
    selectedPlayer: Player?,
    selectPlayerToSubstitute: (player: ManagerEntry) -> Unit,
    selectCaptain: (player: Player) -> Unit,
    selectViceCaptain: (player: Player) -> Unit,
    closeBottomSheet: () -> Unit,
    playerToSub: Player?,
    cancelSubstitution: () -> Unit,
    makeSubstitution: (player: ManagerEntry) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = 5,
            horizontalArrangement = Arrangement.Center,
        ) {
            for (i in starters.indices) {
                if (i in 1..11 &&
                    starters[i].player.elementType != starters[i - 1].player.elementType
                ) {
                    Spacer(Modifier.fillMaxWidth())
                }

                PlayerView(
                    modifier = Modifier.width(size).padding(top = 4.dp, end = 2.dp),
                    player = starters[i].player,
                    gameWeek = gameWeek,
                    size = size,
                    isCaptain = starters[i].isCaptain,
                    isViceCaptain = starters[i].isViceCaptain,
                    playerToSub = playerToSub,
                    isPotentialSub = starters[i].isPotentialSub,
                    position = starters[i].position,
                    onPlayerClick = {
                        when {
                            playerToSub == null -> selectPlayer(starters[i].player)
                            playerToSub == starters[i].player -> cancelSubstitution()
                            starters[i].isPotentialSub -> makeSubstitution(starters[i])
                            else -> {}
                        }
                    },
                )
            }
        }
        Text(
            "Bench",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(substitutes.size) { i ->
                PlayerView(
                    modifier = Modifier.width(size).padding(top = 4.dp, end = 2.dp),
                    player = substitutes[i].player,
                    isPotentialSub = substitutes[i].isPotentialSub,
                    gameWeek = gameWeek,
                    size = size,
                    playerToSub = playerToSub,
                    position = substitutes[i].position,
                    onPlayerClick = {
                        when {
                            playerToSub == null -> selectPlayer(substitutes[i].player)
                            playerToSub == substitutes[i].player -> cancelSubstitution()
                            substitutes[i].isPotentialSub -> makeSubstitution(substitutes[i])
                            else -> {}
                        }

                    },
                )
            }
        }

        if (selectedPlayer != null) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxWidth(),
                onDismissRequest = closeBottomSheet,
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            ) {
                MiniPlayerProfile(
                    modifier = Modifier.padding(16.dp),
                    player = selectedPlayer,
                    onSubsClick = {
                        val playerToSubstitute =
                            (starters + substitutes).find { it.player.id == selectedPlayer.id }

                        if (playerToSubstitute != null) {
                            selectPlayerToSubstitute(playerToSubstitute)
                        }
                    },
                    onCaptainClick = { selectCaptain(selectedPlayer) },
                    onViceCaptainClick = { selectViceCaptain(selectedPlayer) }
                )
            }
        }
    }
}