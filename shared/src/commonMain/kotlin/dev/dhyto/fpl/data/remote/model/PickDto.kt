package dev.dhyto.fpl.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PickDto(
    val element: Int?,
    @SerialName("is_captain")
    val isCaptain: Boolean?,
    @SerialName("is_vice_captain")
    val isViceCaptain: Boolean?,
    val multiplier: Int?,
    val position: Int?,
    @SerialName("selling_price")
    val sellingPrice: Int?,
    @SerialName("purchase_price")
    val purchasePrice: Int?
)