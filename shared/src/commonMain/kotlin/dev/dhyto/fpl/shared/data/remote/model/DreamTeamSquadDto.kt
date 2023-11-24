package dev.dhyto.fpl.shared.data.remote.model
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class DreamTeamSquadDto(
    val team: List<DreamTeamMember>,
    @SerialName("top_player")
    val topPlayer: TopPlayer
)

@Serializable
data class DreamTeamMember(
    val element: Int,
    val points: Int,
    val position: Int
)

@Serializable
data class TopPlayer(
    val id: Int,
    val points: Int
)