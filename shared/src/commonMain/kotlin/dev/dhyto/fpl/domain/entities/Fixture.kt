package dev.dhyto.fpl.shared.domain.entities

data class Fixture(
    val code: Int,
    val gameWeek: Int,
    val id: Int?,
    val teamHome: Team,
    val teamAway: Team,
    val teamHScore: Int?,
    val teamAScore: Int?,
    val kickOffTime: String,
)
