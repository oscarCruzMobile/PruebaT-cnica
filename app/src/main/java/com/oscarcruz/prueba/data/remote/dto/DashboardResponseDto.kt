package com.oscarcruz.prueba.data.remote.dto

import kotlinx.serialization.Serializable
@Serializable
data class DashboardRequest(
    val usuario: String,
    val idTipoAcuerdo: Int = 1,
    val dummy: Boolean = false
)

@Serializable
data class DashboardResponse(
    val codigo: Int,
    val mensaje: String,
    val resultado: List<AcuerdoItem>
)

@Serializable
data class AcuerdoItem(
    val estatus: String,
    val idEstatus: Int,
    val orden: Int,
    val total: Int
)