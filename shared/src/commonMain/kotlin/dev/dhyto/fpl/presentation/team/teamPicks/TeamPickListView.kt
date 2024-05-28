package dev.dhyto.fpl.presentation.team.teamPicks

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    closeBottomSheet: () -> Unit,
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
                    starters = teamSelectionState.starters,
                    substitutes = teamSelectionState.substitutes,
                    size = size.dp,
                    gameWeek = gameWeek,
                    selectPlayer = selectPlayer,
                    selectedPlayer = selectedPlayer,
                    selectPlayerToSubstitute = {
                        teamSelectionState.selectedPlayerForSubstitution.value = it
                        closeBottomSheet()
                    },
                    selectCaptain = { teamSelectionState.selectCaptain(it); closeBottomSheet() },
                    selectViceCaptain = { teamSelectionState.selectViceCaptain(it); closeBottomSheet() },
                    closeBottomSheet = closeBottomSheet,
                    playerToSub = teamSelectionState.selectedPlayerForSubstitution.value?.player,
                    cancelSubstitution = {
                        teamSelectionState.selectedPlayerForSubstitution.value = null
                    },
                    makeSubstitution = teamSelectionState::makeSubstitution
                )
            }
        }
    }
}