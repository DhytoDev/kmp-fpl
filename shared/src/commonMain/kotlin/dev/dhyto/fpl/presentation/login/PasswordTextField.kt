package dev.dhyto.fpl.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import fpl_app.shared.generated.resources.Res
import fpl_app.shared.generated.resources.hide_password
import fpl_app.shared.generated.resources.label_password
import fpl_app.shared.generated.resources.show_password
import org.jetbrains.compose.resources.stringResource

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    password: String?,
    onPasswordChanged: (password: String) -> Unit,
    onDoneClicked: () -> Unit,
    enabled: Boolean = true,
) {
    var isPasswordHidden by remember {
        mutableStateOf(true)
    }

    TextField(
        modifier = modifier,
        value = password ?: "",
        onValueChange = onPasswordChanged,
        singleLine = true,
        enabled = enabled,
        label = {
            Text(text = stringResource(Res.string.label_password))
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Lock, contentDescription = null)
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable(
                    onClickLabel = if (isPasswordHidden) {
                        stringResource(Res.string.show_password)
                    } else stringResource(Res.string.hide_password)
                ) {
                    isPasswordHidden = !isPasswordHidden
                },
                imageVector = if (isPasswordHidden) {
                    Icons.Default.Visibility
                } else Icons.Default.VisibilityOff,
                contentDescription = null
            )
        },
        keyboardActions = KeyboardActions(
            onDone = {
                onDoneClicked()
            }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = if (isPasswordHidden) {
            PasswordVisualTransformation()
        } else VisualTransformation.None
    )
}