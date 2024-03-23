package dev.dhyto.fpl.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class History(
    val assists: Int,
    val bonus: Int,
    val bps: Int,
    @SerialName("clean_sheets")
    val cleanSheets: Int,
    val creativity: String,
    val element: Int,
    @SerialName("expected_assists")
    val expectedAssists: String,
    @SerialName("expected_goal_involvements")
    val expectedGoalInvolvements: String,
    @SerialName("expected_goals")
    val expectedGoals: String,
    @SerialName("expected_goals_conceded")
    val expectedGoalsConceded: String,
    val fixture: Int,
    @SerialName("goals_conceded")
    val goalsConceded: Int,
    @SerialName("goals_scored")
    val goalsScored: Int,
    @SerialName("ict_index")
    val ictIndex: String,
    val influence: String,
    @SerialName("kickoff_time")
    val kickoffTime: String,
    val minutes: Int,
    @SerialName("opponent_team")
    val opponentTeam: Int,
    @SerialName("own_goals")
    val ownGoals: Int,
    @SerialName("penalties_missed")
    val penaltiesMissed: Int,
    @SerialName("penalties_saved")
    val penaltiesSaved: Int,
    @SerialName("red_cards")
    val redCards: Int,
    val round: Int,
    val saves: Int,
    val selected: Int,
    val starts: Int,
    @SerialName("team_a_score")
    val teamAScore: Int,
    @SerialName("team_h_score")
    val teamHScore: Int,
    val threat: String,
    @SerialName("total_points")
    val totalPoints: Int,
    @SerialName("transfers_balance")
    val transfersBalance: Int,
    @SerialName("transfers_in")
    val transfersIn: Int,
    @SerialName("transfers_out")
    val transfersOut: Int,
    val value: Int,
    @SerialName("was_home")
    val wasHome: Boolean,
    @SerialName("yellow_cards")
    val yellowCards: Int
)