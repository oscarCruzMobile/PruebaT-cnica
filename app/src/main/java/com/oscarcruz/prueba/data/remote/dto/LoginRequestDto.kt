package com.oscarcruz.prueba.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val usuario: String,
    val dispositivo: String,
    val ip: String,
    val transferir: Boolean = false,
    val token: String,
    val clave: String
)