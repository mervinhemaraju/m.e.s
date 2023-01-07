package com.th3pl4gu3.mes.data.network

import com.th3pl4gu3.mes.api.MesApiService
import com.th3pl4gu3.mes.models.MesResponse

/**
 * Repository that fetch service list from Mes API.
 */
interface MesServiceRepository {
    /** Fetches list of services from Mes API */
    suspend fun getMesServices(): MesResponse
}

/**
 * Network Implementation of Repository that fetch services from Mes API.
 */
class MesApiServiceRepository(

    private val mesApiService: MesApiService

) : MesServiceRepository {
    /** Fetches list of Services from Mes API */
    override suspend fun getMesServices(): MesResponse = mesApiService.getServices()
}