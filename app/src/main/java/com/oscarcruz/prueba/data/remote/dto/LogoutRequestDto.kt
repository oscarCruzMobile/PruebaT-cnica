package com.oscarcruz.prueba.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LogoutRequestDto(
    val idSession: String
)