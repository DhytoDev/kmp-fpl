package dev.dhyto.fpl.shared.data.remote.model


import dev.dhyto.fpl.shared.domain.entities.ManagerInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ManagerInfoDto(
    @SerialName("current_event")
    val currentEvent: Int,
    @SerialName("entered_events")
    val enteredEvents: List<Int>,
    @SerialName("favourite_team")
    val favouriteTeam: Int,
    val id: Int,
    @SerialName("joined_time")
    val joinedTime: String,
    val kit: String,
    @SerialName("last_deadline_bank")
    val lastDeadlineBank: Int,
    @SerialName("last_deadline_total_transfers")
    val lastDeadlineTotalTransfers: Int,
    @SerialName("last_deadline_value")
    val lastDeadlineValue: Int,
//    val leagues: Leagues,
    val name: String,
    @SerialName("name_change_blocked")
    val nameChangeBlocked: Boolean,
    @SerialName("player_first_name")
    val playerFirstName: String,
    @SerialName("player_last_name")
    val playerLastName: String,
    @SerialName("player_region_id")
    val playerRegionId: Int,
    @SerialName("player_region_iso_code_long")
    val playerRegionIsoCodeLong: String,
    @SerialName("player_region_iso_code_short")
    val playerRegionIsoCodeShort: String,
    @SerialName("player_region_name")
    val playerRegionName: String,
    @SerialName("started_event")
    val startedEvent: Int,
    @SerialName("summary_event_points")
    val summaryEventPoints: Int,
    @SerialName("summary_event_rank")
    val summaryEventRank: Int,
    @SerialName("summary_overall_points")
    val summaryOverallPoints: Int,
    @SerialName("summary_overall_rank")
    val summaryOverallRank: Int,
) {
    fun toManagerInfo(): ManagerInfo {
        return ManagerInfo(
            currentGameWeek = this.currentEvent,
            name = this.name,
            summaryGwPoints = this.summaryEventPoints,
            summaryGwRanks = this.summaryEventRank,
            summaryOverallPoints = this.summaryOverallPoints,
            summaryOverallRank = this.summaryOverallRank,
        )
    }
}