package dev.dhyto.fpl.presentation.team

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import co.touchlab.kermit.Logger
import dev.dhyto.fpl.domain.entities.ManagerEntry
import dev.dhyto.fpl.domain.entities.Player

@Composable
fun rememberTeamSelectionState(teamSelection: List<ManagerEntry> = emptyList()): TeamSelectionState {
    return remember { TeamSelectionState(teamSelection) }
}

class TeamSelectionState(
    private val teamSelection: List<ManagerEntry>,
) {
    val starters = teamSelection.subList(0, 11).toMutableStateList()

    val substitutes = teamSelection.subList(11, 15)

    private val captain = mutableStateOf(starters.first { it.isCaptain })

    private val viceCaptain = mutableStateOf(starters.first { it.isViceCaptain })

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

    private fun swapCaptain() {
        val currentCaptain = captain.value
        val currentViceCaptain = viceCaptain.value
        captain.value = currentViceCaptain.copy(isCaptain = true, isViceCaptain = false)
        viceCaptain.value = currentCaptain.copy(isViceCaptain = true, isCaptain = false)
        starters[starters.indexOf(currentCaptain)] = captain.value
        starters[starters.indexOf(currentViceCaptain)] = viceCaptain.value

        starters.forEachIndexed { index, managerEntry ->
            Logger.i("Starters[${managerEntry.position}] = ${managerEntry.player.name}")
        }
    }

}