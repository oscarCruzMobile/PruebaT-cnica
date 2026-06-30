package com.oscarcruz.prueba.domain.usecase

import com.oscarcruz.prueba.domain.model.UserSession
import com.oscarcruz.prueba.domain.repository.AuthRepository

import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    // Usamos 'operator fun invoke' para llamarlo como loginUseCase(...)
    suspend operator fun invoke(user: String, pass: String): Result<UserSession> {
        return repository.login(user, pass)
    }
}