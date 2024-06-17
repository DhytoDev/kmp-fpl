package dev.dhyto.fpl.data.repositories

import dev.dhyto.fpl.data.remote.model.PickDto
import dev.dhyto.fpl.domain.entities.ManagerEntry
import dev.dhyto.fpl.domain.entities.Player

object TeamPicksMapper {
    fun PickDto?.toManagerEntry(player: Player) = ManagerEntry(
        player = player,
        isCaptain = this?.isCaptain ?: false,
        isViceCaptain = this?.isViceCaptain ?: false,
        multiplier = this?.multiplier ?: 1,
        position = this?.position ?: 1,
        sellingPrice = this?.sellingPrice?.div(10)?.toDouble() ?: 0.0,
        purchasePrice = this?.purchasePrice?.div(10)?.toDouble() ?: 0.0,
    )

    fun ManagerEntry.toPickDto() = PickDto(
        element = this.player.id,
        isCaptain = this.isCaptain,
        isViceCaptain = this.isViceCaptain,
        multiplier = this.multiplier,
        position = this.position,
        sellingPrice = (this.sellingPrice * 10).toInt() ,
        purchasePrice = (this.purchasePrice * 10).toInt(),
    )
}