package dev.dhyto.fpl.shared.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CupMatchesDto(
    @SerialName("entry_1_draw")
    val entry1Draw: Int,
    @SerialName("entry_1_entry")
    val entry1Entry: Int,
    @SerialName("entry_1_loss")
    val entry1Loss: Int,
    @SerialName("entry_1_name")
    val entry1Name: String,
    @SerialName("entry_1_player_name")
    val entry1PlayerName: String,
    @SerialName("entry_1_points")
    val entry1Points: Int,
    @SerialName("entry_1_total")
    val entry1Total: Int,
    @SerialName("entry_1_win")
    val entry1Win: Int,
    @SerialName("entry_2_draw")
    val entry2Draw: Int,
    @SerialName("entry_2_entry")
    val entry2Entry: Int,
    @SerialName("entry_2_loss")
    val entry2Loss: Int,
    @SerialName("entry_2_name")
    val entry2Name: String,
    @SerialName("entry_2_player_name")
    val entry2PlayerName: String,
    @SerialName("entry_2_points")
    val entry2Points: Int,
    @SerialName("entry_2_total")
    val entry2Total: Int,
    @SerialName("entry_2_win")
    val entry2Win: Int,
    val event: Int,
    val id: Int,
    @SerialName("is_bye")
    val isBye: Boolean,
    @SerialName("is_knockout")
    val isKnockout: Boolean,
    @SerialName("knockout_name")
    val knockoutName: String,
    val league: Int,
//    @SerialName("seed_value")
//    val seedValue: Any?,
//    val tiebreak: Any?,
    val winner: Int
)