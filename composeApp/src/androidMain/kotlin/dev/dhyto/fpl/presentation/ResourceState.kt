package dev.dhyto.fpl.presentation

sealed class ResourceState<out T> {
    data object InitialState : ResourceState<Nothing>()
    data object LoadingState : ResourceState<Nothing>()
    data class SuccessState<T>(val data: T) : ResourceState<T>()
    data class ErrorState(val message: String) : ResourceState<Nothing>()
}
