package com.oscarcruz.prueba.domain.repository

import com.oscarcruz.prueba.domain.model.UserSession

interface AuthRepository {
    suspend fun login(user: String, pass: String): Result<UserSession>
}