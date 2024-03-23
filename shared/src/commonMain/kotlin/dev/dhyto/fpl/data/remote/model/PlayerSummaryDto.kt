package dev.dhyto.fpl.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerSummaryDto(
    val fixtures: List<FixtureDto>,
    val history: List<History>,
    @SerialName("history_past")
    val historyPast: List<HistoryPast>,
)