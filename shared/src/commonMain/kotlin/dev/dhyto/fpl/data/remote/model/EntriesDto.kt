package dev.dhyto.fpl.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EntriesDto(
    @SerialName("active_chip")
    val activeChip: String?,
    @SerialName("automatic_subs")
    val automaticSubs: List<AutomaticSubDto?>?,
    @SerialName("entry_history")
    val entryHistory: EntryHistoryDto?,
    val picks: List<PickDto?>?
)