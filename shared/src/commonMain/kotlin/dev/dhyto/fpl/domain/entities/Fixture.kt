package dev.dhyto.fpl.domain.entities

data class Fixture(
    val code: Int,
    val gameWeek: Int?,
    val id: Int?,
    val teamHome: Team,
    val teamAway: Team,
    val teamHScore: Int?,
    val teamAScore: Int?,
    val kickOffTime: String,
    val difficulty: Int?,
    val isHome: Boolean?,
)

//"id": 296,
//"code": 2367833,
//"team_h": 13,
//"team_h_score": null,
//"team_a": 1,
//"team_a_score": null,
//"event": 30,
//"finished": false,
//"minutes": 0,
//"provisional_start_time": false,
//"kickoff_time": "2024-03-31T15:30:00Z",
//"event_name": "Gameweek 30",
//"is_home": true,
//"difficulty": 5
