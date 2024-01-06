package dev.dhyto.fpl.shared.presentation.summary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.dhyto.fpl.shared.domain.entities.ManagerInfo

@Composable
internal fun SummaryCard(
    modifier: Modifier,
    managerInfo: ManagerInfo,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    ) {
        SummaryContent(
            modifier = Modifier.padding(16.dp),
            managerInfo = managerInfo
        )
    }
}

@Composable
internal fun SummaryContent(
    modifier: Modifier,
    managerInfo: ManagerInfo,
) {
    Column(modifier = modifier) {
        Text(
            "Gameweek ${managerInfo.currentGameWeek}",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            item {
                SummaryItem(
                    title = "GW Points", value = managerInfo.summaryGwPoints
                )
            }
            item {
                SummaryItem(
                    title = "Overall Points", value = managerInfo.summaryOverallPoints
                )
            }
            item {
                SummaryItem(
                    title = "GW Rank", value = managerInfo.summaryGwRanks
                )
            }
            item {
                SummaryItem(
                    title = "Overall Rank", value = managerInfo.summaryOverallRank
                )
            }
        }
    }
}

@Composable
fun SummaryItem(
    title: String,
    value: Int,
) {
    Column {
        Text(value.toString(), style = MaterialTheme.typography.titleLarge)
        Text(title, style = MaterialTheme.typography.bodyLarge)
    }
}