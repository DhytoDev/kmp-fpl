package dev.dhyto.fpl.data.local.mapper

import dev.dhyto.fpl.domain.entities.Player
import dev.dhyto.fpl.domain.entities.PlayerPosition.Companion.getPlayerPosition
import dev.dhyto.fpl.shared.databases.PlayerEntity
import dev.dhyto.fpl.shared.databases.TeamEntity

fun PlayerEntity.mapToDomain(team: TeamEntity) : Player {
    return Player(
        id = this.id.toInt(),
        name = this.fullName,
        displayName = this.displayName,
        team = team.mapToDomainTeam(),
        photoUrl = "${Player.BASE_PHOTO_URL}/p${this.code}.png",
        points = this.totalPoints.toInt(),
        elementType = this.elementType?.toInt(),
        playerStats = null,
        playerPosition = this.elementType?.toInt()?.getPlayerPosition()
    );
}