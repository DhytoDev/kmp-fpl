package dev.dhyto.fpl.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    @SerialName("average_entry_score")
    val averageEntryScore: Int,
    @SerialName("chip_plays")
    val chipPlays: List<ChipPlay>,
    @SerialName("cup_leagues_created")
    val cupLeaguesCreated: Boolean,
    @SerialName("data_checked")
    val dataChecked: Boolean,
    @SerialName("deadline_time")
    val deadlineTime: String,
    @SerialName("deadline_time_epoch")
    val deadlineTimeEpoch: Int,
    @SerialName("deadline_time_game_offset")
    val deadlineTimeGameOffset: Int,
    val finished: Boolean,
    @SerialName("h2h_ko_matches_created")
    val h2hKoMatchesCreated: Boolean,
    @SerialName("highest_score")
    val highestScore: Int?,
    @SerialName("highest_scoring_entry")
    val highestScoringEntry: Int?,
    val id: Int,
    @SerialName("is_current")
    val isCurrent: Boolean,
    @SerialName("is_next")
    val isNext: Boolean,
    @SerialName("is_previous")
    val isPrevious: Boolean,
    @SerialName("most_captained")
    val mostCaptained: Int?,
    @SerialName("most_selected")
    val mostSelected: Int?,
    @SerialName("most_transferred_in")
    val mostTransferredIn: Int?,
    @SerialName("most_vice_captained")
    val mostViceCaptained: Int?,
    val name: String,
    @SerialName("top_element")
    val topElement: Int?,
    @SerialName("top_element_info")
    val topElementInfo: TopElementInfo?,
    @SerialName("transfers_made")
    val transfersMade: Int?
)