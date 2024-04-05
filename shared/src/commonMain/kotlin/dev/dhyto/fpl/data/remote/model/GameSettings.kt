package dev.dhyto.fpl.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameSettings(
    @SerialName("cup_qualifying_method")
    val cupQualifyingMethod: String?,
    @SerialName("cup_start_event_id")
    val cupStartEventId: Int?,
    @SerialName("cup_stop_event_id")
    val cupStopEventId: Int?,
    @SerialName("cup_type")
    val cupType: String?,
    @SerialName("league_h2h_tiebreak_stats")
    val leagueH2hTiebreakStats: List<String>,
    @SerialName("league_join_private_max")
    val leagueJoinPrivateMax: Int,
    @SerialName("league_join_public_max")
    val leagueJoinPublicMax: Int,
    @SerialName("league_ko_first_instead_of_random")
    val leagueKoFirstInsteadOfRandom: Boolean,
    @SerialName("league_max_ko_rounds_private_h2h")
    val leagueMaxKoRoundsPrivateH2h: Int,
    @SerialName("league_max_size_private_h2h")
    val leagueMaxSizePrivateH2h: Int,
    @SerialName("league_max_size_public_classic")
    val leagueMaxSizePublicClassic: Int,
    @SerialName("league_max_size_public_h2h")
    val leagueMaxSizePublicH2h: Int,
    @SerialName("league_points_h2h_draw")
    val leaguePointsH2hDraw: Int,
    @SerialName("league_points_h2h_lose")
    val leaguePointsH2hLose: Int,
    @SerialName("league_points_h2h_win")
    val leaguePointsH2hWin: Int,
    @SerialName("league_prefix_public")
    val leaguePrefixPublic: String,
    @SerialName("squad_squadplay")
    val squadSquadplay: Int,
    @SerialName("squad_squadsize")
    val squadSquadsize: Int,
    @SerialName("squad_team_limit")
    val squadTeamLimit: Int,
    @SerialName("squad_total_spend")
    val squadTotalSpend: Int,
    @SerialName("stats_form_days")
    val statsFormDays: Int,
    @SerialName("sys_vice_captain_enabled")
    val sysViceCaptainEnabled: Boolean,
    val timezone: String,
    @SerialName("transfers_cap")
    val transfersCap: Int,
    @SerialName("transfers_sell_on_fee")
    val transfersSellOnFee: Double,
    @SerialName("ui_currency_multiplier")
    val uiCurrencyMultiplier: Int,
    @SerialName("ui_use_special_shirts")
    val uiUseSpecialShirts: Boolean
)