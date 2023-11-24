package dev.dhyto.fpl.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.dhyto.fpl.shared.domain.entities.Player
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(resourceState: ResourceState<List<Player>>) {
    MaterialTheme {
        when (resourceState) {
            is ResourceState.ErrorState -> Text(text = resourceState.message)
            is ResourceState.InitialState -> CircularProgressIndicator()
            is ResourceState.LoadingState -> CircularProgressIndicator()
            is ResourceState.SuccessState<List<Player>> -> Column {
                Text("Dream Team")
                LazyRow {
                    itemsIndexed(resourceState.data) { _, player ->
                        DreamTeamCardView(playerName = player.name)
                    }
                }
            }
        }


    }
}

@Composable
fun DreamTeamCardView(
    playerName: String,
) {
    Card(
        modifier = Modifier
            .padding(end = 8.dp)
            .size(100.dp)
    ) {
        Column {
            Text(playerName)
        }
    }
}