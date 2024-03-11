package dev.dhyto.fpl.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AutomaticSubDto(
    @SerialName("element_in")
    val elementIn: Int?,
    @SerialName("element_out")
    val elementOut: Int?,
    val entry: Int?,
    val event: Int?
)