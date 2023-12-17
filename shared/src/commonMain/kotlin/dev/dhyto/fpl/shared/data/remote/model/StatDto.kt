package dev.dhyto.fpl.shared.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class StatDto(
    val a: List<MatchStatDto>,
    val h: List<MatchStatDto>,
    val identifier: String
)