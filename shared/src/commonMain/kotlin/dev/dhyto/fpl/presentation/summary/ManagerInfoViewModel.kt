package dev.dhyto.fpl.shared.presentation.summary

import dev.dhyto.fpl.shared.domain.entities.ManagerInfo
import dev.dhyto.fpl.shared.domain.repositories.IFplRepository
import dev.dhyto.fpl.shared.presentation.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class ManagerInfoViewModel(
    private val fplRepository: IFplRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<ManagerInfo>>(UiState.InitialState)

    val state = _state.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UiState.LoadingState)

    fun getManagerInfo(managerId: Int = 2545290) {
        viewModelScope.launch {
            fplRepository.getManagerInfo(managerId).fold(
                ifLeft = { failure ->
                    _state.emit(UiState.ErrorState(failure))
                },
                ifRight = { managerInfo ->
                    _state.emit(UiState.SuccessState(managerInfo))
                }
            )
        }
    }
}
