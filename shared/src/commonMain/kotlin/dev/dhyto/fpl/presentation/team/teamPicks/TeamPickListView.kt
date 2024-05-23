package dev.dhyto.fpl.presentation.team.teamPicks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
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
import dev.dhyto.fpl.domain.base.Failure
import dev.dhyto.fpl.domain.entities.ManagerEntry
import dev.dhyto.fpl.domain.entities.Player
import dev.dhyto.fpl.presentation.UiState
import dev.dhyto.fpl.presentation.navigation.NavigationRoute
import dev.dhyto.fpl.presentation.team.MyTeamEvent
import dev.dhyto.fpl.presentation.team.rememberTeamSelectionState
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator


@Composable
fun TeamPickListView(
    state: UiState<List<ManagerEntry>>,
    eventHandler: (event: MyTeamEvent) -> Unit,
    navigator: Navigator,
    gameWeek: Int,
    selectedPlayer: Player?,
    selectPlayer: (player: Player) -> Unit,
    onBottomSheetClosed: () -> Unit,
) {

    when (state) {
        UiState.InitialState, UiState.LoadingState -> {
            CircularProgressIndicator()
        }

        is UiState.ErrorState -> {
            if (state.failure is Failure.UnauthenticatedFailure) {
                UnauthenticatedLayout(modifier = Modifier.padding(16.dp), onClick = {
                    navigator.navigate(
                        NavigationRoute.SignInRoute.route, NavOptions(launchSingleTop = true)
                    )
                })
            }
        }

        is UiState.SuccessState<List<ManagerEntry>> -> {

            val teamSelectionState = rememberTeamSelectionState(state.data)

            BoxWithConstraints {
                val size = 65 * 720 / constraints.maxWidth

                TeamPicksBody(
                    modifier = Modifier.fillMaxWidth(),
                    starters = teamSelectionState.starters.sortedBy { it.position },
                    substitutes = teamSelectionState.substitutes,
                    size = size.dp,
                    gameWeek = gameWeek,
                    selectPlayer = selectPlayer,
                    selectedPlayer = selectedPlayer,
                    onSubsClick = {},
                    selectCaptain = { teamSelectionState.selectCaptain(it); onBottomSheetClosed() },
                    selectViceCaptain = { teamSelectionState.selectViceCaptain(it); onBottomSheetClosed() },
                    closeBottomSheet = onBottomSheetClosed
                )
            }
        }
    }
}

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
    onSubsClick: () -> Unit = {},
    selectCaptain: (player: Player) -> Unit = {},
    selectViceCaptain: (player: Player) -> Unit = {},
    closeBottomSheet: () -> Unit = {}
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
                    photoUrl = starters[i].player.photoUrl,
                    playerName = starters[i].player.displayName,
                    playerId = starters[i].player.id!!,
                    gameWeek = gameWeek,
                    size = size,
                    isCaptain = starters[i].isCaptain,
                    isViceCaptain = starters[i].isViceCaptain,
                    onPlayerClick = {
                        selectPlayer(starters[i].player)
                    }
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
                    photoUrl = substitutes[i].player.photoUrl,
                    playerName = substitutes[i].player.displayName,
                    playerId = substitutes[i].player.id!!,
                    gameWeek = gameWeek,
                    size = size,
                )
            }
        }

        if (selectedPlayer != null) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxWidth(),
                onDismissRequest = { closeBottomSheet() },
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            ) {
                MiniPlayerProfile(
                    modifier = Modifier.padding(16.dp),
                    player = selectedPlayer,
                    onSubsClick = onSubsClick,
                    onCaptainClick = { selectCaptain(selectedPlayer) },
                    onViceCaptainClick = { selectViceCaptain(selectedPlayer) }
                )
            }
        }
    }
}