package dev.dhyto.fpl.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Phase(
    val id: Int,
    val name: String,
    @SerialName("start_event")
    val startEvent: Int,
    @SerialName("stop_event")
    val stopEvent: Int
)