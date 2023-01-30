package com.th3pl4gu3.mes.data.network

import com.th3pl4gu3.mes.api.MesApiService
import com.th3pl4gu3.mes.models.MesResponse

/**
 * Network Implementation of Repository that fetch services from Mes API.
 */
class MesApiServiceRepository(

    private val mesApiService: MesApiService

) : ServiceRepository {
    /** Fetches list of Services from Mes API */
    override suspend fun getMesServices(): MesResponse = mesApiService.getMesServices()
}