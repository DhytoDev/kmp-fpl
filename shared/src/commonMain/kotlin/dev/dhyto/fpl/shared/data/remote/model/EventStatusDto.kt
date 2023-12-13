package dev.dhyto.fpl.shared.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class EventStatusDto(
    val leagues: String,
    val status: List<StatusDto>
)