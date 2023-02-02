package com.th3pl4gu3.mauritius_emergency_services.api

import com.th3pl4gu3.mauritius_emergency_services.models.MesResponse
import retrofit2.http.GET

/**
 * A public interface that exposes the [getAllServices] method
 */
interface MesApiService {
    /**
     * Returns a [MesResponse] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "services" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("services")
    suspend fun getAllServices(): MesResponse
}