package dev.dhyto.fpl.domain.entities

data class ManagerEntry(
    val player: Player,
    val isCaptain: Boolean = false,
    val isViceCaptain: Boolean = false,
    val multiplier: Int = 1,
    val position: Int = 1,
    val sellingPrice: Double = 0.0,
    val purchasePrice: Double = 0.0,
    val isPotentialSub: Boolean = false,
    val isStarter : Boolean = false,
)


