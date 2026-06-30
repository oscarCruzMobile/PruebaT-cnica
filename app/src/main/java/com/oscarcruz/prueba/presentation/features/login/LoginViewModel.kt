package com.oscarcruz.prueba.presentation.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oscarcruz.prueba.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase // 1. Inyectamos el UseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<LoginUiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.EmailChanged    -> _uiState.update { it.copy(email = action.value) }
            is LoginAction.PasswordChanged -> _uiState.update { it.copy(password = action.value) }
            LoginAction.TogglePassword     -> _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
            LoginAction.Submit             -> loginUser()
        }
    }

    private fun loginUser() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = loginUseCase(uiState.value.email, uiState.value.password)

            result.onSuccess {
                _uiEvent.send(LoginUiEvent.NavigateToDashboard)
            }.onFailure { error ->
                _uiEvent.send(LoginUiEvent.ShowSnackbar(
                    message = "Error al hacer login" // Ahora usamos el nombre 'message'
                ))
            }

            _uiState.update { it.copy(isLoading = false) }
        }
    }
}