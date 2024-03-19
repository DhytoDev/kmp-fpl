package dev.dhyto.fpl.presentation.login

import dev.dhyto.fpl.domain.repositories.IFplAuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class SignInViewModel(
    private val fplAuthRepository: IFplAuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthenticationState())

    val uiState = _uiState.asStateFlow()

    fun handleEvent(event: AuthenticationEvent) {
        when (event) {
            AuthenticationEvent.Authenticate -> {
                _uiState.value = _uiState.value.copy(isLoading = true)

                viewModelScope.launch {
                    if (_uiState.value.email != null && _uiState.value.password != null) {

                        val result = fplAuthRepository.signIn(
                            _uiState.value.email!!,
                            _uiState.value.password!!
                        )

                        result.fold(
                            ifLeft = {
                                _uiState.value = _uiState.value.copy(
                                    authenticated = false,
                                    isLoading = false,
                                    error = it.message
                                )
                            },
                            ifRight = {
                                _uiState.value =
                                    _uiState.value.copy(authenticated = true, isLoading = false)
                            }
                        )
                    }
                }
            }

            is AuthenticationEvent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(email = event.email)
            }

            is AuthenticationEvent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(password = event.password)
            }
        }
    }
}

data class AuthenticationState(
    val email: String? = null,
    val password: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val authenticated: Boolean = false,
)

sealed interface AuthenticationEvent {
    class EmailChanged(val email: String) : AuthenticationEvent

    class PasswordChanged(val password: String) : AuthenticationEvent

    data object Authenticate : AuthenticationEvent
}