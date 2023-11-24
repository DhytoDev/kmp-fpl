package dev.dhyto.fpl.shared.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Element(
    val assists: Int,
    val bonus: Int,
    val bps: Int,
    @SerialName("chance_of_playing_next_round")
    val chanceOfPlayingNextRound: Int?,
    @SerialName("chance_of_playing_this_round")
    val chanceOfPlayingThisRound: Int?,
    @SerialName("clean_sheets")
    val cleanSheets: Int,
    @SerialName("clean_sheets_per_90")
    val cleanSheetsPer90: Double,
    val code: Int,
    @SerialName("corners_and_indirect_freekicks_order")
    val cornersAndIndirectFreekicksOrder: Int?,
    @SerialName("corners_and_indirect_freekicks_text")
    val cornersAndIndirectFreekicksText: String,
    @SerialName("cost_change_event")
    val costChangeEvent: Int,
    @SerialName("cost_change_event_fall")
    val costChangeEventFall: Int,
    @SerialName("cost_change_start")
    val costChangeStart: Int,
    @SerialName("cost_change_start_fall")
    val costChangeStartFall: Int,
    val creativity: String,
    @SerialName("creativity_rank")
    val creativityRank: Int,
    @SerialName("creativity_rank_type")
    val creativityRankType: Int,
    @SerialName("direct_freekicks_order")
    val directFreekicksOrder: Int?,
    @SerialName("direct_freekicks_text")
    val directFreekicksText: String,
    @SerialName("dreamteam_count")
    val dreamteamCount: Int,
    @SerialName("element_type")
    val elementType: Int,
    @SerialName("ep_next")
    val epNext: String,
    @SerialName("ep_this")
    val epThis: String,
    @SerialName("event_points")
    val eventPoints: Int,
    @SerialName("expected_assists")
    val expectedAssists: String,
    @SerialName("expected_assists_per_90")
    val expectedAssistsPer90: Double,
    @SerialName("expected_goal_involvements")
    val expectedGoalInvolvements: String,
    @SerialName("expected_goal_involvements_per_90")
    val expectedGoalInvolvementsPer90: Double,
    @SerialName("expected_goals")
    val expectedGoals: String,
    @SerialName("expected_goals_conceded")
    val expectedGoalsConceded: String,
    @SerialName("expected_goals_conceded_per_90")
    val expectedGoalsConcededPer90: Double,
    @SerialName("expected_goals_per_90")
    val expectedGoalsPer90: Double,
    @SerialName("first_name")
    val firstName: String,
    val form: String,
    @SerialName("form_rank")
    val formRank: Int,
    @SerialName("form_rank_type")
    val formRankType: Int,
    @SerialName("goals_conceded")
    val goalsConceded: Int,
    @SerialName("goals_conceded_per_90")
    val goalsConcededPer90: Double,
    @SerialName("goals_scored")
    val goalsScored: Int,
    @SerialName("ict_index")
    val ictIndex: String,
    @SerialName("ict_index_rank")
    val ictIndexRank: Int,
    @SerialName("ict_index_rank_type")
    val ictIndexRankType: Int,
    val id: Int,
    @SerialName("in_dreamteam")
    val inDreamTeam: Boolean,
    val influence: String,
    @SerialName("influence_rank")
    val influenceRank: Int,
    @SerialName("influence_rank_type")
    val influenceRankType: Int,
    val minutes: Int,
    val news: String,
    @SerialName("news_added")
    val newsAdded: String?,
    @SerialName("now_cost")
    val nowCost: Int,
    @SerialName("now_cost_rank")
    val nowCostRank: Int,
    @SerialName("now_cost_rank_type")
    val nowCostRankType: Int,
    @SerialName("own_goals")
    val ownGoals: Int,
    @SerialName("penalties_missed")
    val penaltiesMissed: Int,
    @SerialName("penalties_order")
    val penaltiesOrder: Int?,
    @SerialName("penalties_saved")
    val penaltiesSaved: Int,
    @SerialName("penalties_text")
    val penaltiesText: String,
    val photo: String,
    @SerialName("points_per_game")
    val pointsPerGame: String,
    @SerialName("points_per_game_rank")
    val pointsPerGameRank: Int,
    @SerialName("points_per_game_rank_type")
    val pointsPerGameRankType: Int,
    @SerialName("red_cards")
    val redCards: Int,
    val saves: Int,
    @SerialName("saves_per_90")
    val savesPer90: Double,
    @SerialName("second_name")
    val secondName: String,
    @SerialName("selected_by_percent")
    val selectedByPercent: String,
    @SerialName("selected_rank")
    val selectedRank: Int,
    @SerialName("selected_rank_type")
    val selectedRankType: Int,
    val special: Boolean,
    @SerialName("squad_number")
    val squadNumber: Int?,
    val starts: Int,
    @SerialName("starts_per_90")
    val startsPer90: Double,
    val status: String,
    val team: Int,
    @SerialName("team_code")
    val teamCode: Int,
    val threat: String,
    @SerialName("threat_rank")
    val threatRank: Int,
    @SerialName("threat_rank_type")
    val threatRankType: Int,
    @SerialName("total_points")
    val totalPoints: Int,
    @SerialName("transfers_in")
    val transfersIn: Int,
    @SerialName("transfers_in_event")
    val transfersInEvent: Int,
    @SerialName("transfers_out")
    val transfersOut: Int,
    @SerialName("transfers_out_event")
    val transfersOutEvent: Int,
    @SerialName("value_form")
    val valueForm: String,
    @SerialName("value_season")
    val valueSeason: String,
    @SerialName("web_name")
    val webName: String,
    @SerialName("yellow_cards")
    val yellowCards: Int
)