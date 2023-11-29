package dev.dhyto.fpl.shared.domain.base

sealed class Failure(open val message: String?) {
    data class NetworkFailure(override val message: String?) : Failure(message)
}