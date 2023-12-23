package dev.dhyto.fpl.shared.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FixtureDto(
    val code: Int,
    val event: Int,
    val finished: Boolean,
    @SerialName("finished_provisional")
    val finishedProvisional: Boolean,
    val id: Int,
    @SerialName("kickoff_time")
    val kickoffTime: String,
    val minutes: Int,
    @SerialName("provisional_start_time")
    val provisionalStartTime: Boolean,
    @SerialName("pulse_id")
    val pulseId: Int,
    val started: Boolean,
    val stats: List<StatDto>,
    @SerialName("team_a")
    val teamA: Int,
    @SerialName("team_a_difficulty")
    val teamADifficulty: Int,
    @SerialName("team_a_score")
    val teamAScore: Int?,
    @SerialName("team_h")
    val teamH: Int,
    @SerialName("team_h_difficulty")
    val teamHDifficulty: Int,
    @SerialName("team_h_score")
    val teamHScore: Int?,
    val difficulty: Int?,
)