package dev.dhyto.fpl.shared.domain.entities

data class Player(
    val id: Int,
    val name: String,
    val team: Team,
    val photoUrl: String = "",
    val points: Int,
    val currentPrice: Double = 0.0,
    val goalsScored: Int = 0,
    val assists: Int = 0,
    val elementType: Int?,
) {
    companion object {
        const val basePhotoUrl =
            "https://resources.premierleague.com/premierleague/photos/players/110x140"
    }

    fun getPosition(): String {
        return when (elementType) {
            1 -> "GK"
            2 -> "DEF"
            3 -> "MID"
            4 -> "FWD"
            else -> ""
        }
    }
}
