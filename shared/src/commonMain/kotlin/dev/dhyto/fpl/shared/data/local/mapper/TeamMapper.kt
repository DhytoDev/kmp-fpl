package dev.dhyto.fpl.shared.data.local.mapper

import dev.dhyto.fpl.shared.databases.TeamEntity
import dev.dhyto.fpl.shared.domain.entities.Team

fun TeamEntity.mapToDomainTeam(): Team {
    return Team(
        id = this.id.toInt(),
        name = this.name,
        shortName = this.shortName,
        code = this.code.toInt(),
        teamBadgeUrl = "https://resources.premierleague.com/premierleague/badges/t${this.code}.png"
    )
}