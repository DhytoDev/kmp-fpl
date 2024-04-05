package dev.dhyto.fpl.presentation.team

import dev.dhyto.fpl.domain.entities.ManagerEntry
import dev.dhyto.fpl.domain.usecases.GetMyTeam
import dev.dhyto.fpl.presentation.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class MyTeamViewModel(
    private val getMyTeam: GetMyTeam,
) : ViewModel() {
    private val _state = MutableStateFlow<UiState<List<ManagerEntry>>>(UiState.LoadingState)

    val state = _state.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UiState.LoadingState)


    fun handleEvent(event: MyTeamEvent) {
        when(event) {
            is MyTeamEvent.GetMyTeam -> {
                viewModelScope.launch {
                    getMyTeam.invoke().fold(
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

sealed interface MyTeamEvent {
    class GetMyTeam(val managerId: Int) : MyTeamEvent
}