package com.th3pl4gu3.mauritius_emergency_services.data.network

import com.th3pl4gu3.mauritius_emergency_services.api.MesApiService
import com.th3pl4gu3.mauritius_emergency_services.models.responses.MesCycloneReportResponse
import com.th3pl4gu3.mauritius_emergency_services.models.responses.MesServicesResponse

/**
 * Network Implementation of Repository that fetch services from Mes API.
 */
class MesApiNetworkServiceRepository(
    private val mesApiService: MesApiService
) : NetworkServiceRepository {
    override suspend fun getAllServices(language: String): MesServicesResponse = mesApiService.getAllServices(language = language)

    override suspend fun getCycloneReport(language: String): MesCycloneReportResponse = mesApiService.getCycloneReport(language = language)
}