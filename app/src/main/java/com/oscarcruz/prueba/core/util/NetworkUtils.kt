package com.oscarcruz.prueba.core.util


import java.net.InetAddress
import java.net.NetworkInterface
import java.util.Collections

object NetworkUtils {

    /**
     * Obtiene la dirección IP actual del dispositivo.
     * Itera sobre las interfaces de red para encontrar la primera IP válida (no local).
     */
    fun getDeviceIpAddress(): String {
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            for (intf in Collections.list(interfaces)) {
                val addresses = intf.inetAddresses
                for (addr in Collections.list(addresses)) {
                    // Filtramos para obtener direcciones IPv4 y excluir la dirección de loopback (localhost)
                    if (!addr.isLoopbackAddress && addr is java.net.Inet4Address) {
                        return addr.hostAddress ?: "0.0.0.0"
                    }
                }
            }
        } catch (e: Exception) {
            // En caso de error, devolvemos un valor por defecto o manejamos la excepción
            return "0.0.0.0"
        }
        return "0.0.0.0"
    }
}