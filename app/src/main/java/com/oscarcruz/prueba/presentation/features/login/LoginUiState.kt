package com.oscarcruz.prueba.presentation.features.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val passwordVisible: Boolean = false,
) {

}