package dev.dhyto.fpl.shared.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopElementInfo(
    val id: Int,
    val points: Int
)