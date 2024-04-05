package dev.dhyto.fpl.presentation

import dev.dhyto.fpl.domain.base.Failure

sealed class UiState<out T> {
    data object InitialState : UiState<Nothing>()
    data object LoadingState : UiState<Nothing>()
    data class SuccessState<T>(val data: T) : UiState<T>()
    data class ErrorState(val failure: Failure?) : UiState<Nothing>()
}
