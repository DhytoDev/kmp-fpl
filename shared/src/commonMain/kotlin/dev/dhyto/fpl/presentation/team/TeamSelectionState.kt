package dev.dhyto.fpl.presentation.team

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import dev.dhyto.fpl.domain.entities.ManagerEntry
import dev.dhyto.fpl.domain.entities.Player
import dev.dhyto.fpl.domain.entities.PlayerPosition

@Composable
fun rememberTeamSelectionState(teamSelection: List<ManagerEntry> = emptyList()): TeamSelectionState {
    return remember { TeamSelectionState(teamSelection) }
}

class TeamSelectionState(teamSelection: List<ManagerEntry>) {

    val starters = teamSelection.filter { it.isStarter }.toMutableStateList()

    val substitutes = teamSelection.filter { !it.isStarter }.toMutableStateList()

    private val captain = mutableStateOf(starters.first { it.isCaptain })

    private val viceCaptain = mutableStateOf(starters.first { it.isViceCaptain })

    var selectedPlayerForSubstitution = kotlin.run {
        val state = mutableStateOf<ManagerEntry?>(null)
        object : MutableState<ManagerEntry?> by state {
            override var value: ManagerEntry?
                get() = state.value
                set(value) {
                    state.value = value
                    if (value != null) setPotentialSubsForPlayer(value)
                    else resetPotentialSubs()
                }
        }
    }


    fun selectCaptain(player: Player) {
        val newCaptain = starters.find { it.player == player }
        val currentCaptain = captain.value

        if (newCaptain == currentCaptain || newCaptain == null) return

        if (newCaptain == viceCaptain.value) {
            swapCaptain()
        } else {
            captain.value = newCaptain.copy(isCaptain = true)
            starters[starters.indexOf(currentCaptain)] = currentCaptain.copy(isCaptain = false)
            starters[starters.indexOf(newCaptain)] = captain.value
        }
    }

    fun selectViceCaptain(player: Player) {
        val newViceCaptain = starters.find { it.player == player }
        val currentViceCaptain = viceCaptain.value

        if (newViceCaptain == null || newViceCaptain == currentViceCaptain) return

        if (newViceCaptain == captain.value) {
            swapCaptain()
        } else {
            viceCaptain.value = newViceCaptain.copy(isViceCaptain = true)
            starters[starters.indexOf(currentViceCaptain)] =
                currentViceCaptain.copy(isViceCaptain = false)
            starters[starters.indexOf(newViceCaptain)] = viceCaptain.value
        }
    }

    fun makeSubstitution(replacementPlayer: Player) {

    private fun resetPotentialSubs() {
        (starters + substitutes).map { entry ->
            entry.copy(isPotentialSub = false)
        }.let { managerEntries ->
            starters.swapList(managerEntries.filter { it.isStarter })
            substitutes.swapList(managerEntries.filter { !it.isStarter })
        }
    }

    private fun setPotentialSubsForPlayer(selectedPlayerForSubstitution: ManagerEntry) {
        val startersGroupByPosition = starters.groupBy { it.player.playerPosition }

        when {
            selectedPlayerForSubstitution.player.playerPosition == PlayerPosition.GKP -> {
                setPotentialSubsForGK(selectedPlayerForSubstitution.player)
            }

            selectedPlayerForSubstitution.isStarter -> {
                setPotentialSubsForBenchedPlayer(
                    selectedPlayerForSubstitution = selectedPlayerForSubstitution,
                    startersGroupByPosition = startersGroupByPosition
                )
            }

            else -> {
                val startersCopy = starters.map { entry ->
                    val isPotentialSub = canRemovePlayerFromPosition(
                        entry.player.playerPosition!!,
                        startersGroupByPosition[entry.player.playerPosition]!!.size
                    )

                    if (isPotentialSub) entry.copy(isPotentialSub = true)
                    else entry
                }

                starters.swapList(startersCopy)

                val substitutesCopy = substitutes.map { entry ->
                    val isPotentialSub = entry.player.playerPosition != PlayerPosition.GKP
                            && entry.player.id != selectedPlayerForSubstitution.player.id

                    if (isPotentialSub) entry.copy(isPotentialSub = true)
                    else entry
                }

                substitutes.swapList(substitutesCopy)
            }
        }
    }

    private fun setPotentialSubsForBenchedPlayer(
        selectedPlayerForSubstitution: ManagerEntry,
        startersGroupByPosition: Map<PlayerPosition?, List<ManagerEntry>>
    ) {
        val playerPosition = selectedPlayerForSubstitution.player.playerPosition!!

        val canSubOut = canRemovePlayerFromPosition(
            playerPosition,
            startersGroupByPosition[playerPosition]!!.size
        )

        val substitutesCopy = substitutes.map { entry ->
            val isPotentialSub =
                if (canSubOut) entry.player.playerPosition != PlayerPosition.GKP
                else entry.player.playerPosition == selectedPlayerForSubstitution.player.playerPosition

            if (isPotentialSub) entry.copy(isPotentialSub = true)
            else entry
        }

        substitutes.swapList(substitutesCopy)
    }

    private fun setPotentialSubsForGK(selectedPlayerForSubstitution: Player) {
        (starters + substitutes).map { entry ->
            val isPotentialSub =
                entry.player.playerPosition == selectedPlayerForSubstitution.playerPosition
                        && entry.player != selectedPlayerForSubstitution

            if (isPotentialSub) entry.copy(isPotentialSub = true)
            else entry
        }.let { managerEntries ->
            starters.swapList(managerEntries.filter { it.isStarter })
            substitutes.swapList(managerEntries.filter { !it.isStarter })
        }
    }

    private fun swapCaptain() {
        val currentCaptain = captain.value
        val currentViceCaptain = viceCaptain.value
        captain.value = currentViceCaptain.copy(isCaptain = true, isViceCaptain = false)
        viceCaptain.value = currentCaptain.copy(isViceCaptain = true, isCaptain = false)
        starters[starters.indexOf(currentCaptain)] = captain.value
        starters[starters.indexOf(currentViceCaptain)] = viceCaptain.value
    }

    private fun canRemovePlayerFromPosition(
        position: PlayerPosition,
        currentNumOfPlayersAtPos: Int
    ): Boolean {
        return when (position) {
            PlayerPosition.GKP -> false
            PlayerPosition.DEF -> currentNumOfPlayersAtPos > MIN_NUM_OF_DEF
            PlayerPosition.FWD -> currentNumOfPlayersAtPos > MIN_NUM_OF_FWD
            else -> true
        }
    }

    companion object {
        const val MIN_NUM_OF_DEF = 3
        const val MIN_NUM_OF_FWD = 1
    }

}

fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
    clear()
    addAll(newList)
}