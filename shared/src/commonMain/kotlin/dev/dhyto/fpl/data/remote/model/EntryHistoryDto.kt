package dev.dhyto.fpl.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EntryHistoryDto(
    val bank: Int?,
    val event: Int?,
    @SerialName("event_transfers")
    val eventTransfers: Int?,
    @SerialName("event_transfers_cost")
    val eventTransfersCost: Int?,
    @SerialName("overall_rank")
    val overallRank: Int?,
    @SerialName("percentile_rank")
    val percentileRank: Int?,
    val points: Int?,
    @SerialName("points_on_bench")
    val pointsOnBench: Int?,
    val rank: Int?,
    @SerialName("rank_sort")
    val rankSort: Int?,
    @SerialName("total_points")
    val totalPoints: Int?,
    val value: Int?
)