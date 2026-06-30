package com.oscarcruz.prueba.presentation.features.login
sealed class LoginUiEvent {
    data class ShowSnackbar(
        val message: String
    ) : LoginUiEvent()
    data object NavigateToDashboard : LoginUiEvent()
}