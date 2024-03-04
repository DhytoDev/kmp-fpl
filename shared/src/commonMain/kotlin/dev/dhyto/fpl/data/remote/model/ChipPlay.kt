package dev.dhyto.fpl.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChipPlay(
    @SerialName("chip_name")
    val chipName: String,
    @SerialName("num_played")
    val numPlayed: Int
)