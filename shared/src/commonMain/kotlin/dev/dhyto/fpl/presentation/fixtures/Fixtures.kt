package dev.dhyto.fpl.presentation.fixtures

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.dhyto.fpl.core.components.shimmerEffect
import dev.dhyto.fpl.domain.entities.Fixture
import dev.dhyto.fpl.utils.formatDate
import dev.dhyto.fpl.utils.kickOffDayString
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun FixturesSection(
    modifier: Modifier,
    showLoading: Boolean = true,
    fixtures: List<Fixture>,
) {
    Column {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Fixtures", style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.weight(0.5F))
                Text("See All", style = MaterialTheme.typography.bodySmall)
            }
        }
        LazyColumn(modifier) {
            if (showLoading) {
                items(5) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .shimmerEffect()
                            .fillMaxWidth()
                            .height(30.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            itemsIndexed(fixtures.take(5)) { _, fixture ->
                FixtureItem(
                    fixture = fixture,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
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
                    modifier = Modifier.size(35.dp),
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
                    modifier = Modifier.size(35.dp),
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