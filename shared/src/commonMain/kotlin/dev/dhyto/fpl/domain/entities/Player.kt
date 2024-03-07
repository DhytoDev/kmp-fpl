package dev.dhyto.fpl.domain.entities

data class Player(
    val id: Int?,
    val name: String,
    val displayName: String,
    val team: Team,
    val photoUrl: String = "",
    val points: Int,
    val goalsScored: Int = 0,
    val assists: Int = 0,
    val elementType: Int?,
    val price: Double = 0.0,
    val totalPoints: Int = 0,
    val code: Int = 0,
    val cleanSheets: Int = 0,
    val goalsConceded: Int = 0,
    val saves: Int = 0,
    val yellowCards: Int = 0,
    val redCards: Int = 0,
) {
    companion object {
        const val BASE_PHOTO_URL =
            "https://resources.premierleague.com/premierleague/photos/players/110x140"

        fun dummyPlayers(): List<Player> {
            val list = mutableListOf<Player>()

            val teamBadgeUrl = "https://resources.premierleague.com/premierleague/badges/t1.png"


            for (i in 1..11) {
                val elementType: Int = when (i) {
                    1 -> 1
                    in 2..4 -> 2
                    in 5..9 -> 3
                    else -> 4
                }

                list.add(
                    Player(
                        id = i,
                        name = "Name $i",
                        displayName = "Name $i",
                        elementType = elementType,
                        points = i * 2,
                        team = Team(
                            id = 1,
                            name = "Manchester City",
                            shortName = "MCI",
                            code = 1,
                            teamBadgeUrl = teamBadgeUrl
                        )
                    )
                )
            }

            return list
        }
    }

    fun getPosition(): String {
        return when (elementType) {
            1 -> "GKP"
            2 -> "DEF"
            3 -> "MID"
            4 -> "FWD"
            else -> throw Exception("unknown position")
        }
    }
}
