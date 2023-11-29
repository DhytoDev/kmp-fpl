package dev.dhyto.fpl.shared.presentation

sealed class UiState<out T> {
    data object InitialState : UiState<Nothing>()
    data object LoadingState : UiState<Nothing>()
    data class SuccessState<T>(val data: T) : UiState<T>()
    data class ErrorState(val message: String) : UiState<Nothing>()
}
