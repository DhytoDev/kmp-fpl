package dev.dhyto.fpl.shared.domain.entities

data class Team(
    val id:Int?,
    val name: String = "",
    val shortName: String = "",
    val code: Int = 0,
    val teamBadgeUrl: String = "",
)
