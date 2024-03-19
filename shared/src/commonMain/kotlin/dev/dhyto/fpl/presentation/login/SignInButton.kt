package dev.dhyto.fpl.presentation.login

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fpl_app.shared.generated.resources.Res
import fpl_app.shared.generated.resources.label_login_button
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SignInButton(
    modifier: Modifier,
    enabled: Boolean = false,
    onSubmit: () -> Unit
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onSubmit
    ) {
        Text(stringResource(Res.string.label_login_button))
    }
}