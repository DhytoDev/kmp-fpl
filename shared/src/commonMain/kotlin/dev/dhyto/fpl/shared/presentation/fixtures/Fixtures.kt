package dev.dhyto.fpl.shared.presentation.fixtures

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.dhyto.fpl.shared.domain.entities.Fixture
import dev.dhyto.fpl.shared.presentation.UiState
import dev.dhyto.fpl.shared.utils.formatDate
import dev.dhyto.fpl.shared.utils.kickOffDayString
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun FixturesSection(
    fixturesUiState: UiState<List<Fixture>>,
    modifier: Modifier,
) {
    Column {
        Box(
            modifier = modifier
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Fixtures", style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.weight(0.5F))
                Text("See All", style = MaterialTheme.typography.bodySmall)
            }
        }
        when (fixturesUiState) {
            is UiState.ErrorState -> Box {}
            is UiState.InitialState -> Box {}
            is UiState.LoadingState -> Box {}
            is UiState.SuccessState -> LazyColumn {
                itemsIndexed(fixturesUiState.data.take(5)) { _, fixture ->
                    FixtureItem(
                        fixture = fixture,
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

    }

}

@Composable
private fun FixtureItem(
    fixture: Fixture,
    modifier: Modifier,
) {
    val homeBadgeResource = asyncPainterResource(fixture.teamHome.teamBadgeUrl)
    val awayBadgeResource = asyncPainterResource(fixture.teamAway.teamBadgeUrl)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        /*
        * Home Team Scope
        * */
        Box(
            modifier = modifier.weight(0.5F)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    fixture.teamHome.name,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.weight(1.0F))
                KamelImage(
                    modifier = Modifier.size(40.dp),
                    resource = homeBadgeResource,
                    contentDescription = fixture.teamHome.name,
                    contentScale = ContentScale.Fit,
                    contentAlignment = Alignment.TopStart,
                )
                Spacer(modifier = Modifier.width(4.dp))
                if (fixture.teamHScore != null)
                    Text(fixture.teamHScore.toString(), fontSize = 12.sp)
            }
        }

        if (fixture.teamHScore == null && fixture.teamAScore == null)
           Column(
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               Text(fixture.kickOffTime.kickOffDayString(), fontSize = 12.sp)
               Text(fixture.kickOffTime.formatDate("HH:mm"), fontSize = 12.sp)
           }
        else
            Text(" - ", fontSize = 12.sp)

        /*
        * Away Team Scope
        * */
        Box(
            modifier = modifier.weight(0.5F)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (fixture.teamAScore != null)
                    Text(fixture.teamAScore.toString(), fontSize = 12.sp)
                Spacer(modifier = Modifier.width(4.dp))
                KamelImage(
                    modifier = Modifier.size(40.dp),
                    resource = awayBadgeResource,
                    contentDescription = fixture.teamHome.name,
                    contentScale = ContentScale.Fit,
                    contentAlignment = Alignment.TopStart,
                )
                Spacer(modifier = Modifier.weight(1.0F))
                Text(
                    fixture.teamAway.name, style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}