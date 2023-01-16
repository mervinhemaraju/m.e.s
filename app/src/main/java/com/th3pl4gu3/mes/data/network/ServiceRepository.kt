package com.th3pl4gu3.mes.data.network

import com.th3pl4gu3.mes.models.MesResponse

/**
 * Repository that fetch service list from Mes API.
 */
interface ServiceRepository {
    /** Fetches list of services from Mes API */
    suspend fun getMesServices(): MesResponse
}