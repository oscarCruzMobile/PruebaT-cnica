package com.oscarcruz.prueba.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val codigo: Int,
    val mensaje: String,
    val resultado: LoginResultDto? = null
)

@Serializable
data class LoginResultDto(
    val correo: String,
    val idSession: String,
    val nombre: String
)