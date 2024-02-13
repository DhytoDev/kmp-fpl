package dev.dhyto.fpl.shared.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester

@Composable
internal fun SignInScreen() {
    Scaffold {
        Column {
            val passwordFocusRequester = FocusRequester()

            EmailTextField(
                modifier = Modifier.fillMaxWidth(),
                email = null,
                onEmailChanged = {},
                onNextClicked = {
                    passwordFocusRequester.requestFocus()
                }
            )
            PasswordTextField(
                modifier = Modifier.fillMaxWidth().focusRequester(passwordFocusRequester),
                password = "",
                onPasswordChanged = {},
                onDoneClicked = {}
            )
        }
    }
}