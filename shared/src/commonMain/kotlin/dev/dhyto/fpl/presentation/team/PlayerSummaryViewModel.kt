package dev.dhyto.fpl.presentation.team

import dev.dhyto.fpl.domain.entities.UpcomingOpponent
import dev.dhyto.fpl.domain.usecases.GetThreeUpcomingFixtures
import dev.dhyto.fpl.presentation.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class PlayerSummaryViewModel(
    private val getThreeUpcomingFixtures: GetThreeUpcomingFixtures,
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<Map<Int, List<UpcomingOpponent>>>>(UiState.InitialState)

    val state = _state.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UiState.LoadingState)

    fun handleEvent(event: PlayerSummaryEvent) {
        when(event) {
            is PlayerSummaryEvent.GetThreeUpcomingFixtures -> {
                viewModelScope.launch {
                    getThreeUpcomingFixtures.invoke(event.playerId, event.gameWeek).fold(
                        ifLeft = {
                            _state.emit(UiState.ErrorState(it))
                        },
                        ifRight = {
                            _state.emit(UiState.SuccessState(it))
                        }
                    )
                }
            }
        }
    }
}

sealed interface PlayerSummaryEvent {
    data class GetThreeUpcomingFixtures(val playerId: Int, val gameWeek:Int): PlayerSummaryEvent
}