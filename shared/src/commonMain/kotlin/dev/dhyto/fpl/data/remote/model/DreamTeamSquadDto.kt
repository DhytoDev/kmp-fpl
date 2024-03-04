package dev.dhyto.fpl.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class DreamTeamSquadDto(
    val team: List<DreamTeamMember>,
    @SerialName("top_player")
    val topPlayer: TopPlayer
)

@Serializable
data class DreamTeamMember(
    val element: Int = 0,
    val points: Int = 0,
    val position: Int = 0
)

@Serializable
data class TopPlayer(
    val id: Int = 0,
    val points: Int = 0
)