package dev.dhyto.fpl.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeneralInfoDto(
    @SerialName("element_stats")
    val elementStats: List<ElementStat>,
    @SerialName("element_types")
    val elementTypes: List<ElementType>,
    val elements: List<Element>,
    val events: List<Event>,
    @SerialName("game_settings")
    val gameSettings: GameSettings,
    val phases: List<Phase>,
    val teams: List<TeamDto>,
    @SerialName("total_players")
    val totalPlayers: Int
)