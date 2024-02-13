package dev.dhyto.fpl.shared.presentation.team

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun MyTeamTabBar(
    tabItems: List<String>,
    selectedTabIndex: Int,
    onClick: (index: Int) -> Unit,
) {
    tabItems.forEachIndexed { index, title ->
        Tab(modifier = Modifier.padding(12.dp),
            selected = selectedTabIndex == index,
            onClick = { onClick(index) }
        ) {
            Text(title)
        }
    }
}