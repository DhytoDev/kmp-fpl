package dev.dhyto.fpl.shared.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatusDto(
    @SerialName("bonus_added")
    val bonusAdded: Boolean,
    val date: String,
    val event: Int,
    val points: String
)