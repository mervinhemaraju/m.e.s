package com.th3pl4gu3.mauritius_emergency_services.data.network

import com.th3pl4gu3.mauritius_emergency_services.models.responses.MesCycloneGuidelineResponse
import com.th3pl4gu3.mauritius_emergency_services.models.responses.MesCycloneNamesResponse
import com.th3pl4gu3.mauritius_emergency_services.models.responses.MesCycloneReportResponse
import com.th3pl4gu3.mauritius_emergency_services.models.responses.MesServicesResponse

/**
 * Repository that fetch service list from Mes API.
 */
interface NetworkServiceRepository {
    /** Fetches list of services from Mes API **/
    suspend fun getAllServices(language: String): MesServicesResponse

    /** Fetches the cyclone report from Mes API **/
    suspend fun getCycloneReport(language: String): MesCycloneReportResponse

    /** Fetches the cyclone names from Mes API **/
    suspend fun getCycloneNames(language: String): MesCycloneNamesResponse

    /** Fetches the cyclone guidelines from Mes API **/
    suspend fun getCycloneGuidelines(language: String): MesCycloneGuidelineResponse

    /** Fetches the cyclone report from Mes API (Used for testing ONLY) **/
    suspend fun getCycloneReportTesting(language: String): MesCycloneReportResponse
}