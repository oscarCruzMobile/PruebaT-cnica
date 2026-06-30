package com.oscarcruz.prueba.presentation.features.login

sealed class LoginAction {
    data class EmailChanged(val value: String)    : LoginAction()
    data class PasswordChanged(val value: String) : LoginAction()
    data object TogglePassword                    : LoginAction()
    data object Submit                            : LoginAction()
}