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
        val newCaptain = starters.find { it.player == player } ?: return
        val currentCaptain = starters.find { it.isCaptain } ?: return
        if (newCaptain == currentCaptain) return

        val viceCaptain = starters.find { it.isViceCaptain } ?: return

        if (newCaptain == viceCaptain) {
            swapCaptain()
        } else {
            starters[starters.indexOf(currentCaptain)] = currentCaptain.copy(isCaptain = false)
            starters[starters.indexOf(newCaptain)] = newCaptain.copy(isCaptain = true)
        }
    }

    fun selectViceCaptain(player: Player) {
        val newViceCaptain = starters.find { it.player == player } ?: return
        val currentViceCaptain = starters.find { it.isViceCaptain } ?: return
        if (newViceCaptain == currentViceCaptain) return

        val captain = starters.find { it.isCaptain } ?: return

        if (newViceCaptain == captain) {
            swapCaptain()
        } else {
            starters[starters.indexOf(currentViceCaptain)] =
                currentViceCaptain.copy(isViceCaptain = false)
            starters[starters.indexOf(newViceCaptain)] = newViceCaptain.copy(isViceCaptain = true)
        }
    }

    fun makeSubstitution(replacementPlayer: ManagerEntry) {
        val selectedPlayerForSubstitution = this.selectedPlayerForSubstitution.value ?: return

        val arePlayersInSameLineUp =
            selectedPlayerForSubstitution.isStarter == replacementPlayer.isStarter
                    || !selectedPlayerForSubstitution.isStarter == !replacementPlayer.isStarter

        when (arePlayersInSameLineUp) {
            true -> {
                swapPlayerInSameLineUp(
                    selectedPlayerForSubstitution = selectedPlayerForSubstitution,
                    replacementPlayer = replacementPlayer,
                )
            }

            false -> {
                swapPlayerInDifferentLineUp(
                    selectedPlayerForSubstitution = selectedPlayerForSubstitution,
                    replacementPlayer = replacementPlayer,
                )
            }
        }

        this.selectedPlayerForSubstitution.value = null
    }

    private fun swapPlayerInDifferentLineUp(
        selectedPlayerForSubstitution: ManagerEntry,
        replacementPlayer: ManagerEntry
    ) {
        val selectedPlayerPosition = selectedPlayerForSubstitution.position
        val replacementPlayerPosition = replacementPlayer.position

        when (selectedPlayerForSubstitution.isStarter) {
            true -> {
                swapPlayerForStartingPlayer(
                    selectedPlayerForSubstitution = selectedPlayerForSubstitution,
                    replacementPlayer = replacementPlayer,
                    selectedPlayerPosition = selectedPlayerPosition,
                    replacementPlayerPosition = replacementPlayerPosition
                )
            }

            false -> {
                swapPlayerForBenchedPlayer(
                    replacementPlayer = replacementPlayer,
                    selectedPlayerForSubstitution = selectedPlayerForSubstitution,
                    selectedPlayerPosition = selectedPlayerPosition,
                    replacementPlayerPosition = replacementPlayerPosition
                )
            }
        }

        if (selectedPlayerForSubstitution.playerPosition() != replacementPlayer.playerPosition())
            rearrangeStartersPosition()
    }

    private fun swapPlayerForBenchedPlayer(
        replacementPlayer: ManagerEntry,
        selectedPlayerForSubstitution: ManagerEntry,
        selectedPlayerPosition: Int,
        replacementPlayerPosition: Int
    ) {
        val isCaptain = replacementPlayer.isCaptain
        val isViceCaptain = replacementPlayer.isViceCaptain

        substitutes[substitutes.indexOf(selectedPlayerForSubstitution)] =
            replacementPlayer.copy(
                isStarter = false,
                position = selectedPlayerPosition,
                isCaptain = false,
                isViceCaptain = false
            )

        starters[starters.indexOf(replacementPlayer)] =
            selectedPlayerForSubstitution.copy(
                isStarter = true,
                isCaptain = isCaptain,
                isViceCaptain = isViceCaptain,
                position = replacementPlayerPosition
            )
    }

    private fun swapPlayerForStartingPlayer(
        selectedPlayerForSubstitution: ManagerEntry,
        replacementPlayer: ManagerEntry,
        selectedPlayerPosition: Int,
        replacementPlayerPosition: Int
    ) {
        val isCaptain = selectedPlayerForSubstitution.isCaptain
        val isViceCaptain = selectedPlayerForSubstitution.isViceCaptain

        starters[starters.indexOf(selectedPlayerForSubstitution)] =
            replacementPlayer.copy(
                isStarter = true,
                isCaptain = isCaptain,
                isViceCaptain = isViceCaptain,
                position = selectedPlayerPosition
            )

        substitutes[substitutes.indexOf(replacementPlayer)] =
            selectedPlayerForSubstitution.copy(
                position = replacementPlayerPosition,
                isStarter = false,
                isCaptain = false,
                isViceCaptain = false
            )
    }

    private fun swapPlayerInSameLineUp(
        selectedPlayerForSubstitution: ManagerEntry,
        replacementPlayer: ManagerEntry,
    ) {
        val selectedPlayerPosition = selectedPlayerForSubstitution.position
        val replacementPlayerPosition = replacementPlayer.position

        substitutes[substitutes.indexOf(selectedPlayerForSubstitution)] =
            replacementPlayer.copy(position = selectedPlayerPosition)

        substitutes[substitutes.indexOf(replacementPlayer)] =
            selectedPlayerForSubstitution.copy(position = replacementPlayerPosition)
    }

    private fun rearrangeStartersPosition() {
        starters.sortBy { it.player.elementType }

        val startersCopy = starters.map { entry ->
            val index = starters.indexOf(entry)

            entry.copy(position = index + 1)
        }

        starters.swapList(startersCopy)
    }

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

        when (selectedPlayerForSubstitution.playerPosition()) {
            PlayerPosition.GKP -> {
                setPotentialSubsForGkp(selectedPlayerForSubstitution.player)
            }

            else -> {
                setPotentialSubsForNonGkp(
                    selectedPlayerForSubstitution = selectedPlayerForSubstitution,
                    startersGroupByPosition = startersGroupByPosition
                )
            }
        }
    }

    private fun setPotentialSubsForNonGkp(
        selectedPlayerForSubstitution: ManagerEntry,
        startersGroupByPosition: Map<PlayerPosition?, List<ManagerEntry>>
    ) {
        val isBenchedPlayer = !selectedPlayerForSubstitution.isStarter

        if (isBenchedPlayer) setPotentialSubsForStartingPlayer(startersGroupByPosition)

        setPotentialSubsForBenchedPlayer(
            isBenchedPlayer = isBenchedPlayer,
            selectedPlayerForSubstitution = selectedPlayerForSubstitution,
            startersGroupByPosition = startersGroupByPosition
        )
    }

    private fun setPotentialSubsForStartingPlayer(startersGroupByPosition: Map<PlayerPosition?, List<ManagerEntry>>) {
        val startersCopy = starters.map { entry ->
            val isPotentialSub = canRemovePlayerFromPosition(
                entry.playerPosition()!!,
                startersGroupByPosition[entry.player.playerPosition]!!.size
            )

            if (isPotentialSub) entry.copy(isPotentialSub = true)
            else entry
        }

        starters.swapList(startersCopy)
    }

    private fun setPotentialSubsForBenchedPlayer(
        isBenchedPlayer: Boolean,
        selectedPlayerForSubstitution: ManagerEntry,
        startersGroupByPosition: Map<PlayerPosition?, List<ManagerEntry>>
    ) {
        val playerPosition = selectedPlayerForSubstitution.playerPosition()!!

        val substitutesCopy = substitutes.map { entry ->
            val isNotGkp = entry.playerPosition() != PlayerPosition.GKP

            val isPotentialSub = when (isBenchedPlayer) {
                true -> isNotGkp && entry.player != selectedPlayerForSubstitution.player
                false -> {
                    val areSamePosition = entry.playerPosition() == playerPosition

                    val canSubOut = canRemovePlayerFromPosition(
                        playerPosition, startersGroupByPosition[playerPosition]!!.size
                    )

                    if (canSubOut) isNotGkp else areSamePosition
                }
            }

            if (isPotentialSub) entry.copy(isPotentialSub = true)
            else entry
        }

        substitutes.swapList(substitutesCopy)
    }

    private fun setPotentialSubsForGkp(selectedPlayerForSubstitution: Player) {
        (starters + substitutes).map { entry ->
            val isPotentialSub =
                entry.playerPosition() == selectedPlayerForSubstitution.playerPosition && entry.player != selectedPlayerForSubstitution

            if (isPotentialSub) entry.copy(isPotentialSub = true)
            else entry
        }.let { managerEntries ->
            starters.swapList(managerEntries.filter { it.isStarter })
            substitutes.swapList(managerEntries.filter { !it.isStarter })
        }
    }

    private fun swapCaptain() {
        val currentCaptain = starters.find { it.isCaptain } ?: return
        val currentViceCaptain = starters.find { it.isViceCaptain } ?: return

        starters[starters.indexOf(currentCaptain)] =
            currentViceCaptain.copy(isCaptain = true, isViceCaptain = false)
        starters[starters.indexOf(currentViceCaptain)] =
            currentCaptain.copy(isViceCaptain = true, isCaptain = false)
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

fun ManagerEntry.playerPosition(): PlayerPosition? = this.player.playerPosition

fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
    clear()
    addAll(newList)
}