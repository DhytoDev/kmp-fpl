package dev.dhyto.fpl.core.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListTile(
    modifier: Modifier = Modifier,
    leading: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit = {}
) {
    Row(modifier) {
        if (leading != null) {
            leading.invoke()
            Spacer(Modifier.width(8.dp))
        }
        content()
        trailing?.invoke()
    }
}