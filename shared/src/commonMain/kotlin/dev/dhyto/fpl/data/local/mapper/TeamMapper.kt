package dev.dhyto.fpl.data.local.mapper

import dev.dhyto.fpl.domain.entities.Team
import dev.dhyto.fpl.shared.databases.TeamEntity

fun TeamEntity.mapToDomainTeam(): Team {
    return Team(
        id = this.id.toInt(),
        name = this.name,
        shortName = this.shortName,
        code = this.code.toInt(),
        teamBadgeUrl = "https://resources.premierleague.com/premierleague/badges/t${this.code}.png"
    )
}