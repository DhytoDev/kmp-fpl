package dev.dhyto.fpl.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.dhyto.fpl.shared.domain.entities.Player
import dev.dhyto.fpl.shared.domain.repositories.IFplRepository
import kotlinx.coroutines.launch
import java.io.IOException

class DreamTeamViewModel(
    private val fplRepository: IFplRepository,
) : ViewModel() {

    var theDreamTeams: ResourceState<List<Player>> by mutableStateOf(ResourceState.InitialState)
        private set

    fun getDreamTeamSquad(gameWeek: Int) {
        viewModelScope.launch {
            theDreamTeams = ResourceState.LoadingState

            theDreamTeams = try {
                val dreamTeamSquad = fplRepository.getDreamTeamSquad(gameWeek)
                ResourceState.SuccessState(dreamTeamSquad)
            } catch (e: IOException) {
                ResourceState.ErrorState(e.message.toString())
            }
        }
    }
}