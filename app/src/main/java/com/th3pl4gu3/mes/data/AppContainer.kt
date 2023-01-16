package com.th3pl4gu3.mes.data

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.th3pl4gu3.mes.api.MesApiService
import com.th3pl4gu3.mes.data.local.MesDatabase
import com.th3pl4gu3.mes.data.local.OfflineServiceRepository
import com.th3pl4gu3.mes.data.network.MesApiServiceRepository
import com.th3pl4gu3.mes.data.network.ServiceRepository as NetworkServiceRepository
import com.th3pl4gu3.mes.data.local.ServiceRepository as LocalServiceRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val onlineServiceRepository: NetworkServiceRepository
    val offlineServiceRepository: LocalServiceRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer(private val context: Context): AppContainer {
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

    override val onlineServiceRepository: NetworkServiceRepository by lazy {
        MesApiServiceRepository(retrofitService)
    }

    override val offlineServiceRepository: LocalServiceRepository by lazy {
        OfflineServiceRepository(MesDatabase.getDatabase(context).serviceDao())
    }
}