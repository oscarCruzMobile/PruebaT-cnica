package com.oscarcruz.prueba.data.repository

import com.oscarcruz.prueba.core.util.NetworkUtils.getDeviceIpAddress
import com.oscarcruz.prueba.data.remote.ApiService
import com.oscarcruz.prueba.data.remote.dto.LoginRequestDto
import com.oscarcruz.prueba.data.remote.dto.LogoutRequestDto
import com.oscarcruz.prueba.data.remote.mapper.toDomain
import com.oscarcruz.prueba.domain.model.UserSession
import com.oscarcruz.prueba.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val sessionRepo: PreferencesRepository
) : AuthRepository {

    override suspend fun login(user: String, pass: String): Result<UserSession> {
        return try {

            val request = LoginRequestDto(
                usuario = user,
                dispositivo = "ID_DISPOSITIVO_12345",
                ip = "192.168.1.1",
                transferir = false,
                token = "GUID",
                clave = pass
            )

            val response = api.login(request)


            if (response.codigo == 409 ||  response.codigo !=2) {
                val sessionId = response.resultado?.idSession
                if (!sessionId.isNullOrEmpty()) {
                    api.logout(LogoutRequestDto(idSession = sessionId))

                    return login(user, pass)
                }
            }


            if (response.codigo == 0 && response.resultado != null) {
                sessionRepo.saveString("id_session", response.resultado.idSession)


                val domainModel = response.toDomain()
                Result.success(domainModel)
            } else {
                Result.failure(Exception(response.mensaje ?: "Error desconocido"))
            }
        } catch (e: Exception) {
            // Captura errores de red (SocketTimeout, UnknownHost, etc.)
            Result.failure(e)
        }
    }
}