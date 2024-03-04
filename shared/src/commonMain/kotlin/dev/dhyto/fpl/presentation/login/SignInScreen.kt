package dev.dhyto.fpl.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import fpl_app.shared.generated.resources.Res
import fpl_app.shared.generated.resources.epl
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun SignInScreen(
    modifier: Modifier,
    state: AuthenticationState,
    eventHandler: (event: AuthenticationEvent) -> Unit,
    navigator: Navigator
) {
    Scaffold {
        LaunchedEffect(state) {
            if (state.authenticated) {
                navigator.goBackWith(state.authenticated)
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                SignInForm(
                    modifier = modifier,
                    email = state.email ?: "",
                    password = state.password ?: "",
                    enabled = !state.isLoading,
                    onEmailChanged = {
                        eventHandler(AuthenticationEvent.EmailChanged((it)))
                    },
                    onPasswordChanged = {
                        eventHandler(AuthenticationEvent.PasswordChanged((it)))
                    },
                    onSubmit = {
                        eventHandler(AuthenticationEvent.Authenticate)
                    },
                )
            }
        }
    }
}

@Composable
internal fun SignInForm(
    modifier: Modifier,
    onEmailChanged: (email: String) -> Unit,
    onPasswordChanged: (password: String) -> Unit,
    onSubmit: () -> Unit,
    email: String = "",
    password: String = "",
    enabled: Boolean = true,
) {
    Column(modifier) {
        val passwordFocusRequester = FocusRequester()

        Image(
            painter = painterResource(Res.drawable.epl),
            contentDescription = null,
        )

        EmailTextField(
            modifier = Modifier.fillMaxWidth(),
            email = email,
            enabled = enabled,
            onEmailChanged = onEmailChanged,
            onNextClicked = {
                passwordFocusRequester.requestFocus()
            },
        )
        Spacer(modifier = Modifier.height(12.dp))
        PasswordTextField(
            modifier = Modifier.fillMaxWidth()
                .focusRequester(passwordFocusRequester),
            password = password,
            enabled = enabled,
            onPasswordChanged = onPasswordChanged,
            onDoneClicked = {}
        )
        Spacer(modifier = Modifier.height(12.dp))
        SignInButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = email.isNotBlank() || password.isNotBlank(),
            onSubmit = onSubmit
        )
    }
}