package com.th3pl4gu3.mauritius_emergency_services.data

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.th3pl4gu3.mauritius_emergency_services.MesApplication
import com.th3pl4gu3.mauritius_emergency_services.api.MesApiService
import com.th3pl4gu3.mauritius_emergency_services.data.local.LocalServiceRepository
import com.th3pl4gu3.mauritius_emergency_services.data.local.MesDatabase
import com.th3pl4gu3.mauritius_emergency_services.data.local.OfflineLocalServiceRepository
import com.th3pl4gu3.mauritius_emergency_services.data.network.MesApiNetworkServiceRepository
import com.th3pl4gu3.mauritius_emergency_services.data.network.NetworkServiceRepository
import com.th3pl4gu3.mauritius_emergency_services.data.store.DataStoreRepository
import com.th3pl4gu3.mauritius_emergency_services.data.store.StoreRepository
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import java.util.*

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val onlineServiceRepository: NetworkServiceRepository
    val offlineServiceRepository: LocalServiceRepository
    val dataStoreServiceRepository: StoreRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer(private val context: Context) : AppContainer {

    companion object {
        private const val TAG = "DEFAULT_APP_CONTAINER"
        private const val BASE_URL = "https://mes.mervinhemaraju.com/api/v1/"
    }

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    @OptIn(ExperimentalSerializationApi::class)
    private fun requireRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: MesApiService by lazy {
        return@lazy requireRetrofit().create(MesApiService::class.java)
    }

    override val onlineServiceRepository: NetworkServiceRepository by lazy {
        MesApiNetworkServiceRepository(retrofitService)
    }

    override val offlineServiceRepository: LocalServiceRepository by lazy {
        OfflineLocalServiceRepository(MesDatabase.getDatabase(context).serviceDao())
    }

    override val dataStoreServiceRepository: StoreRepository by lazy {
        DataStoreRepository(context as MesApplication)
    }
}