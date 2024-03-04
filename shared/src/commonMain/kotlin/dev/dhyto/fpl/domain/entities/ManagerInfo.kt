package dev.dhyto.fpl.domain.entities

data class ManagerInfo(
    val currentGameWeek: Int = 0,
    val name: String = "",
    val summaryGwPoints: Int = 0,
    val summaryGwRanks: Int = 0,
    val summaryOverallPoints: Int = 0,
    val summaryOverallRank: Int = 0,
)
