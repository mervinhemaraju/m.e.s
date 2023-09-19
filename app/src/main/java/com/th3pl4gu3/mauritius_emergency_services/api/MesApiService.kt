package com.th3pl4gu3.mauritius_emergency_services.api

import com.th3pl4gu3.mauritius_emergency_services.models.responses.MesCycloneReportResponse
import com.th3pl4gu3.mauritius_emergency_services.models.responses.MesServicesResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * A public interface that exposes the [getAllServices] method
 */
interface MesApiService {
    /**
     * Returns a [MesServicesResponse] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "services" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("{language}/services")
    suspend fun getAllServices(@Path("language") language: String): MesServicesResponse

    /**
     * Returns a [MesCycloneReportResponse] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "cyclone/report" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("{language}/cyclone/report")
    suspend fun getCycloneReport(@Path("language") language: String): MesCycloneReportResponse
}