package dev.dhyto.fpl.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistoryPast(
    val assists: Int?,
    val bonus: Int?,
    val bps: Int?,
    @SerialName("clean_sheets")
    val cleanSheets: Int?,
    val creativity: String?,
    @SerialName("element_code")
    val elementCode: Int?,
    @SerialName("end_cost")
    val endCost: Int?,
    @SerialName("expected_assists")
    val expectedAssists: String?,
    @SerialName("expected_goal_involvements")
    val expectedGoalInvolvements: String?,
    @SerialName("expected_goals")
    val expectedGoals: String?,
    @SerialName("expected_goals_conceded")
    val expectedGoalsConceded: String?,
    @SerialName("goals_conceded")
    val goalsConceded: Int?,
    @SerialName("goals_scored")
    val goalsScored: Int?,
    @SerialName("ict_index")
    val ictIndex: String?,
    val influence: String?,
    val minutes: Int?,
    @SerialName("own_goals")
    val ownGoals: Int?,
    @SerialName("penalties_missed")
    val penaltiesMissed: Int?,
    @SerialName("penalties_saved")
    val penaltiesSaved: Int?,
    @SerialName("red_cards")
    val redCards: Int?,
    val saves: Int?,
    @SerialName("season_name")
    val seasonName: String?,
    @SerialName("start_cost")
    val startCost: Int?,
    val starts: Int?,
    val threat: String?,
    @SerialName("total_poInts")
    val totalPoInts: Int?,
    @SerialName("yellow_cards")
    val yellowCards: Int?
)