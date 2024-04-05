package dev.dhyto.fpl.presentation.login

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import fpl_app.shared.generated.resources.Res
import fpl_app.shared.generated.resources.label_email
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun EmailTextField(
    modifier: Modifier = Modifier,
    email: String?,
    onEmailChanged: (email: String) -> Unit,
    onNextClicked: () -> Unit,
    enabled: Boolean = true,
) {
    TextField(
        modifier = modifier,
        value = email ?: "",
        onValueChange = onEmailChanged,
        enabled = enabled,
        label = {
            Text(text = stringResource(Res.string.label_email))
        },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                onNextClicked()
            }
        )
    )
}