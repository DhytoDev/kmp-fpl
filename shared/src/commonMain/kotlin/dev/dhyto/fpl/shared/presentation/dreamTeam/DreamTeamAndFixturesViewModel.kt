package dev.dhyto.fpl.shared.presentation.dreamTeam

import dev.dhyto.fpl.shared.domain.entities.Fixture
import dev.dhyto.fpl.shared.domain.entities.Player
import dev.dhyto.fpl.shared.domain.usecases.GetDreamTeamAndFixtures
import dev.dhyto.fpl.shared.presentation.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class DreamTeamAndFixturesViewModel(
    private val getDreamTeamAndFixtures: GetDreamTeamAndFixtures,
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<PlayersAndFixtures>>(UiState.InitialState)

    val state = _state
        .asStateFlow()
        .stateIn(viewModelScope, SharingStarted.Lazily, UiState.LoadingState)

    fun getDreamTeamSquad() {
        viewModelScope.launch {

            var pf = PlayersAndFixtures()

            getDreamTeamAndFixtures.invoke().let {
                it.first.fold(
                    ifLeft = { failure ->
                        _state.emit(UiState.ErrorState(failure.message ?: ""))
                    },
                    ifRight = { players ->
                        pf = pf.copy(players = players)
                        _state.emit(UiState.SuccessState(pf))
                    }
                )

                it.second.fold(
                    ifLeft = { failure ->
                        _state.emit(UiState.ErrorState(failure.message ?: ""))
                    },
                    ifRight = { fixtures ->
                        pf = pf.copy(fixtures = fixtures)
                        _state.emit(UiState.SuccessState(pf))
                    }
                )
            }
        }
    }
}

data class PlayersAndFixtures(
    val players: List<Player> = emptyList(),
    val fixtures: List<Fixture> = emptyList(),
)