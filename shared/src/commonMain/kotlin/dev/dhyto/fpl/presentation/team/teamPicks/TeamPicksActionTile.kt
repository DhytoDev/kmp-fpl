package dev.dhyto.fpl.presentation.team.teamPicks

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.dhyto.fpl.core.components.ListTile

@Composable
fun TeamPicksActionTile(
    modifier: Modifier = Modifier,
    text: String,
    leading: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
) {
    ListTile(
        modifier = Modifier.padding(8.dp).then(modifier),
        leading = leading,
        trailing = trailing
    ) {
        Text(text)
    }
}