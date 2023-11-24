package dev.dhyto.fpl.shared.domain.entities

data class Player(
    val id: Int,
    val name: String,
    val team: String,
    val photoUrl: String = "",
    val points: Int,
    val currentPrice: Double = 0.0,
    val goalsScored: Int = 0,
    val assists: Int = 0
)
