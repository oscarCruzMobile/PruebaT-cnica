package com.oscarcruz.prueba.data.remote

import com.oscarcruz.prueba.data.remote.dto.BaseResponseDto
import com.oscarcruz.prueba.data.remote.dto.DashboardRequest
import com.oscarcruz.prueba.data.remote.dto.DashboardResponse
import com.oscarcruz.prueba.data.remote.dto.LoginRequestDto
import com.oscarcruz.prueba.data.remote.dto.LoginResponseDto
import com.oscarcruz.prueba.data.remote.dto.LogoutRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto

    @POST("logout")
    suspend fun logout(@Body request: LogoutRequestDto): BaseResponseDto

    @POST("ConsultaDashboard")
    suspend fun getDashboard(@Body request: DashboardRequest): DashboardResponse
}