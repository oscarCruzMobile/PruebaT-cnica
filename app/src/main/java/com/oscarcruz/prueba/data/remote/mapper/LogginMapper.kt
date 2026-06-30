package com.oscarcruz.prueba.data.remote.mapper

import com.oscarcruz.prueba.data.remote.dto.LoginResponseDto
import com.oscarcruz.prueba.domain.model.UserSession
// Cambiamos de UserSession? a UserSession
fun LoginResponseDto.toDomain(): UserSession {
    val res = this.resultado ?: throw Exception("Datos de sesión no encontrados en la respuesta")
    return UserSession(
        idSession = res.idSession,
        userName = res.nombre,
        email = res.correo
    )
}