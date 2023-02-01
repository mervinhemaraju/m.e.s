package com.th3pl4gu3.mauritius_emergency_services.data.network

import com.th3pl4gu3.mauritius_emergency_services.models.MesResponse

/**
 * Repository that fetch service list from Mes API.
 */
interface ServiceRepository {
    /** Fetches list of services from Mes API */
    suspend fun getMesServices(): MesResponse
}