package com.th3pl4gu3.mes.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.th3pl4gu3.mes.api.MesApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val mesServiceRepository: MesServiceRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://mes.mervinhemaraju.com/api/v1/en/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: MesApiService by lazy {
        retrofit.create(MesApiService::class.java)
    }

    /**
     * DI implementation for services repository
     */
    override val mesServiceRepository: MesServiceRepository by lazy {
        MesApiServiceRepository(retrofitService)
    }
}