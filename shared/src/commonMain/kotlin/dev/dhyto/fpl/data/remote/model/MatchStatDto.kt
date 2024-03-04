package dev.dhyto.fpl.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class MatchStatDto(
    val element: Int,
    val value: Int
)