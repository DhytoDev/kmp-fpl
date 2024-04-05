package dev.dhyto.fpl.domain.base

sealed class Failure(open val message: String?) {
    data class NetworkFailure(
        override val message: String?,
        private val cause: Throwable? = null,
    ) : Failure(message)

    data class LocalFailure(override val message: String?) : Failure(message)
    data class UnauthenticatedFailure(
        override val message: String =
            FailureConst.UNAUTHENTICATED_FAILURE,
    ) : Failure(message)
}

object FailureConst {
    const val UNAUTHENTICATED_FAILURE: String = "unauthenticated"
    const val SERVICE_UNAVAILABLE: String = "ServiceUnavailable"
}