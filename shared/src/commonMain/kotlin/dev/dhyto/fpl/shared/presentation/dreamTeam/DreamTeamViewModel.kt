package dev.dhyto.fpl.shared.presentation.dreamTeam

import dev.dhyto.fpl.shared.domain.entities.Player
import dev.dhyto.fpl.shared.domain.repositories.IFplRepository
import dev.dhyto.fpl.shared.presentation.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class DreamTeamViewModel(
    private val fplRepository: IFplRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<Player>>>(UiState.InitialState)

    val state: StateFlow<UiState<List<Player>>> = _state
        .asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UiState.LoadingState)
    
    fun getDreamTeamSquad() {
        viewModelScope.launch {
            fplRepository.currentGameWeek.collect { currentGameWeek ->
                fplRepository.getDreamTeamSquad(currentGameWeek).fold(
                    ifLeft = { _state.emit(UiState.ErrorState(it.message ?: "")) },
                    ifRight = {
                        _state.emit(UiState.SuccessState(it))
                    }
                )
            }
        }
    }
}