package com.oscarcruz.prueba.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponseDto(
    val codigo: Int,
    val mensaje: String,
    val resultado: String? = null
)